package com.kotlin.olena.tvshowsapp.presentation.show.list

import android.util.Log
import androidx.lifecycle.*
import com.kotlin.olena.tvshowsapp.domain.models.ShowGeneralInfo
import com.kotlin.olena.tvshowsapp.domain.usecase.GetShowListUseCase
import com.kotlin.olena.tvshowsapp.domain.usecase.LogoutUseCase
import com.kotlin.olena.tvshowsapp.other.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val listOfShowsUseCase: GetShowListUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        loadListOfShows()
    }

     private fun loadListOfShows() {
        viewModelScope.launch(dispatcherProvider.io()) {
            listOfShowsUseCase.invoke().collect { list ->
                withContext(dispatcherProvider.main()) {
                    _screenState.emit(
                        if (list.isEmpty())
                            ScreenState.Error
                        else ScreenState.Success(list)
                    )
                }
            }
        }
    }

    fun logout() {
        logoutUseCase.logout()
    }

    sealed class ScreenState {
        object Loading: ScreenState()
        class Success(val list: List<ShowGeneralInfo>): ScreenState()
        object Error: ScreenState()
    }
}