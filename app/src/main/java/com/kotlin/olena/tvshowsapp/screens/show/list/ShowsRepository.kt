package com.kotlin.olena.tvshowsapp.screens.show.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.olena.tvshowsapp.data.db.AppDatabase
import com.kotlin.olena.tvshowsapp.data.models.Show
import com.kotlin.olena.tvshowsapp.data.networking.*
import javax.inject.Inject

class ShowsRepository @Inject constructor(val database:AppDatabase, val api:ApiInterface){

    fun getShowsFromServer(page: Int): LiveData<Resource<List<Show>>> {
        return object : NetworkBoundResource<List<Show>, List<Show>>() {
            override fun saveCallResult(item: List<Show>) {
                database.getShowsDao().insertShows(item)
            }

            override fun shouldFetch(data: List<Show>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Show>> {
                return database.getShowsDao().getShows(page)
            }

            override fun createCall(): LiveData<ApiResponse<List<Show>>> {
                return api.getShowsByPage(page)
            }

        }.asLiveData()
    }
}