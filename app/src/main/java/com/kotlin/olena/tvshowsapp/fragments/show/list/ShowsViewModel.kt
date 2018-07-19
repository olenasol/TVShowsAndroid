package com.kotlin.olena.tvshowsapp.fragments.show.list

import android.arch.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kotlin.olena.tvshowsapp.models.ShowModel

class ShowsViewModel:ViewModel(){

    val listShowsObservable:LiveData<MutableList<ShowModel?>> = ShowsRepository().getShowsFromServer(0)

}