package com.kotlin.olena.network

import com.kotlin.olena.tvshowsapp.domain.models.ShowApiModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface{
    @GET("/shows")
    fun getShows(): Call<List<ShowApiModel>>
}