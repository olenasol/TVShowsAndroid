package com.kotlin.olena.tvshowsapp.fragments.show.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kotlin.olena.tvshowsapp.models.ShowModel

class ShowsViewModel : ViewModel() {

    var page: Int = 0
    var listShowsObservable: MutableLiveData<MutableList<ShowModel?>> = MutableLiveData()

    init {
        addToShows()
    }

    fun addToShows() {
        page++
        ShowsRepository().getShowsFromServer(page - 1,listShowsObservable)
    }
}