package com.kotlin.olena.tvshowsapp.fragments.show.list

import androidx.lifecycle.MutableLiveData
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.kotlin.olena.tvshowsapp.rest.ApiClient
import com.kotlin.olena.tvshowsapp.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsRepository {

    fun getShowsFromServer(page: Int,data: MutableLiveData<MutableList<ShowModel?>>) {
        var list = mutableListOf<ShowModel?>()
        if (data.value != null){
            list = data.value!!
            list.removeAt(list.size-1)
        }
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<MutableList<ShowModel?>> = apiService.getShowsByPage(page)
        call.enqueue(object : Callback<MutableList<ShowModel?>> {
            override fun onResponse(call: Call<MutableList<ShowModel?>>?, response: Response<MutableList<ShowModel?>>?) {
                list.addAll(response?.body()!!.asIterable())
                list.add(null)
                data.postValue(list)
            }

            override fun onFailure(call: Call<MutableList<ShowModel?>>?, t: Throwable?) {
                data.value = null
                //TODO handle error
            }
        })
    }
}