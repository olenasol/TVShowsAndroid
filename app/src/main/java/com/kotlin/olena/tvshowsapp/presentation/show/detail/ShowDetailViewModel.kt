package com.kotlin.olena.tvshowsapp.presentation.show.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.olena.tvshowsapp.di.ViewModelAssistedFactory
import com.kotlin.olena.tvshowsapp.domain.models.ShowInfo
import com.kotlin.olena.tvshowsapp.domain.usecase.GetShowById
import com.kotlin.olena.tvshowsapp.other.DispatcherProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShowDetailViewModel(
    private val getShowByIdUseCase: GetShowById,
    private val dispatcherProvider: DispatcherProvider,
    private val stateHandle: SavedStateHandle
) : ViewModel(){

    private val _showDetails = MutableSharedFlow<ShowInfo>()
    val showDetails = _showDetails.asSharedFlow()

    init {
        viewModelScope.launch(dispatcherProvider.io()) {
            _showDetails.emit(getShowByIdUseCase.invoke(stateHandle.get<Int>(ARG_ID) ?:0))
        }
    }
}

class ShowDetailViewModelFactory @Inject constructor(
    private val getShowByIdUseCase: GetShowById,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModelAssistedFactory<ShowDetailViewModel> {
    override fun create(handle: SavedStateHandle) = ShowDetailViewModel(getShowByIdUseCase, dispatcherProvider, handle)
}