package com.kotlin.olena.tvshowsapp.rest

import com.kotlin.olena.tvshowsapp.models.ShowModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{
    @GET("/shows")
    fun getShowsByPage(@Query("page") page:Int):Call<MutableList<ShowModel?>>
}