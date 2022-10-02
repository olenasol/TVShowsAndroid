package com.kotlin.olena.tvshowsapp.presentation.show.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kotlin.olena.tvshowsapp.base.BaseViewModel
import com.kotlin.olena.tvshowsapp.data.models.Show
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.domain.usecase.LogoutUseCase
import javax.inject.Inject

class ShowsViewModel @Inject constructor(application: Application,
                                         private val logoutUseCase: LogoutUseCase,
                                         repo: ShowsRepository) : BaseViewModel(application) {

    private val page: MutableLiveData<Int> = MutableLiveData()
    private val listShowsFromPage: LiveData<Resource<List<Show>>> = Transformations
            .switchMap(page) { input ->
                repo.getShowsFromServer(input)
            }
    val listOfShows = MediatorLiveData<Resource<List<Show>>>()

    init {
        page.postValue(0)
        listOfShows.addSource(listShowsFromPage) { result: Resource<List<Show>> ->
            var fullList = listOfShows.value?.data?.toMutableList()
            result.data?.let {
                if (fullList == null) {
                    fullList = it.toMutableList()
                } else {
                    fullList?.addAll(it)
                }
            }
            listOfShows.postValue(Resource<List<Show>>(result.status, fullList, result.message))
        }
    }

    fun fetchNextPage() {
        page.value?.let {
            page.postValue(it + 1)
        }
    }

    fun logout() {
        logoutUseCase.logout()
    }


    fun setShowToFavourite(pos: Int) {

    }

    fun onSearchInputChanged(newQuery: String?) {

    }
}