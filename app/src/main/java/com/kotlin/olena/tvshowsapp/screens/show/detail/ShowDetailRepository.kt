package com.kotlin.olena.tvshowsapp.screens.show.detail

import androidx.lifecycle.LiveData
import com.kotlin.olena.tvshowsapp.data.db.AppDatabase
import com.kotlin.olena.tvshowsapp.data.models.Show
import com.kotlin.olena.tvshowsapp.data.models.ShowDetails
import com.kotlin.olena.tvshowsapp.data.networking.ApiInterface
import com.kotlin.olena.tvshowsapp.data.networking.ApiResponse
import com.kotlin.olena.tvshowsapp.data.networking.NetworkBoundResource
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import javax.inject.Inject

class ShowDetailRepository @Inject constructor(val database: AppDatabase, val api: ApiInterface){

    fun getShowDetails(id: Int): LiveData<Resource<ShowDetails>> {
        return object : NetworkBoundResource<ShowDetails,ShowDetails>() {
            override fun saveCallResult(item: ShowDetails) {
                database.getShowDetailsDao().insertShowDetail(item)
            }

            override fun shouldFetch(data: ShowDetails?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<ShowDetails> {
                return database.getShowDetailsDao().getShowDetail(id)
            }

            override fun createCall(): LiveData<ApiResponse<ShowDetails>> {
                return api.getShowDetails(id)
            }

        }.asLiveData()
    }
}