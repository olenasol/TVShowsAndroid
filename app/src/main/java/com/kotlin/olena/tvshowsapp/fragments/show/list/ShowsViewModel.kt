package com.kotlin.olena.tvshowsapp.fragments.show.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.olena.tvshowsapp.base.BaseViewModel
import com.kotlin.olena.tvshowsapp.data.db.AppDatabase
import com.kotlin.olena.tvshowsapp.data.models.Show
import com.kotlin.olena.tvshowsapp.data.networking.Resource

class ShowsViewModel (application: Application) : BaseViewModel(application){

    private var page: Int = 0
    var listShowsObservable: LiveData<Resource<List<Show>>> = MutableLiveData()
    var position = 0

    init {
        addToShows()
    }

    fun addToShows() {
        page++
        listShowsObservable = ShowsRepository(AppDatabase.getDatabase(this.getApplication()))
                .getShowsFromServer(page-1)
    }

    fun verifyIfScrollNeeded(firstPos:Int,lastPos:Int):Boolean{
        return if (position == 0)
            false
        else
            position in firstPos..lastPos
    }
    fun setShowToFavourite(pos:Int){
//        val show: Show? = Show(listShowsObservable.value?.get(pos)?.id!!,
//                listShowsObservable.value?.get(pos)?.image!!,
//                listShowsObservable.value?.get(pos)?.isFavourite!!)
//        show?.isFavourite = !show?.isFavourite!!
//        val list: MutableList<Show?> = mutableListOf()
//        list.addAll(listShowsObservable.value!!)
//        list[pos] = show
//        listShowsObservable.postValue(list)
    }
}