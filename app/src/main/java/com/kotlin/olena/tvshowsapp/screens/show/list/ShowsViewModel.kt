package com.kotlin.olena.tvshowsapp.screens.show.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kotlin.olena.tvshowsapp.base.BaseViewModel
import com.kotlin.olena.tvshowsapp.data.db.AppDatabase
import com.kotlin.olena.tvshowsapp.data.models.Show
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.data.networking.Status

class ShowsViewModel(application: Application) : BaseViewModel(application) {

    private val page: MutableLiveData<Int> = MutableLiveData()
    private val listShowsFromPage: LiveData<Resource<List<Show>>> = Transformations
            .switchMap(page) { input ->
                ShowsRepository(AppDatabase.getDatabase(this.getApplication()))
                        .getShowsFromServer(input)
            }
    val listOfShows = MediatorLiveData<Resource<List<Show>>>()

    init {
         page.postValue(0)
        listOfShows.addSource(listShowsFromPage) { result: Resource<List<Show>> ->
            var fullList = listOfShows.value?.data?.toMutableList()
            result.data?.let{
                if(fullList == null){
                    fullList = it.toMutableList()
                } else{
                    fullList?.addAll(it)
                }
            }
            listOfShows.postValue(Resource<List<Show>>(result.status,fullList,result.message))
        }
    }

    fun fetchNextPage() {
        page.value?.let {
            page.postValue(it + 1)
        }
    }


    fun setShowToFavourite(pos: Int) {
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