package com.kotlin.olena.tvshowsapp.fragments.show.list

import android.arch.lifecycle.LiveData
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.kotlin.olena.tvshowsapp.rest.ApiClient
import com.kotlin.olena.tvshowsapp.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.arch.lifecycle.MutableLiveData

class ShowsRepository{

    fun getShowsFromServer(page:Int):LiveData<MutableList<ShowModel?>>{
        val data = MutableLiveData<MutableList<ShowModel?>>()
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<MutableList<ShowModel?>> = apiService.getShowsByPage(page)
        call.enqueue(object : Callback<MutableList<ShowModel?>> {
            override fun onResponse(call: Call<MutableList<ShowModel?>>?, response: Response<MutableList<ShowModel?>>?) {
                data.value = response?.body()
            }

            override fun onFailure(call: Call<MutableList<ShowModel?>>?, t: Throwable?) {
                data.value = null
                //TODO handle error
            }
        })
        return data
    }
}