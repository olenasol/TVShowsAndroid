package com.kotlin.olena.tvshowsapp.fragments.show.detail

import android.arch.lifecycle.MutableLiveData
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.kotlin.olena.tvshowsapp.rest.ApiClient
import com.kotlin.olena.tvshowsapp.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailRepository {

    fun getShowDetail(id: Int, data: MutableLiveData<ShowModel>) {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<ShowModel> = apiService.getShowDetails(id)
        call.enqueue(object : Callback<ShowModel> {
            override fun onFailure(call: Call<ShowModel>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<ShowModel>?, response: Response<ShowModel>?) {
                data.postValue(response?.body())
            }

        })
    }
}

