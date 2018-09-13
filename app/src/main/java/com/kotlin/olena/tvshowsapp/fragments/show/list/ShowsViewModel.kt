package com.kotlin.olena.tvshowsapp.fragments.show.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kotlin.olena.tvshowsapp.models.ShowModel

class ShowsViewModel : ViewModel() {

    var page: Int = 0
    var listShowsObservable: MutableLiveData<MutableList<ShowModel?>> = MutableLiveData()
    var position = 0

    init {
        addToShows()
    }

    fun addToShows() {
        page++
        ShowsRepository().getShowsFromServer(page - 1,listShowsObservable)
    }

    fun verifyIfScrollNeeded(firstPos:Int,lastPos:Int):Boolean{
        return if (position == 0)
            false
        else
            position in firstPos..lastPos
    }
}