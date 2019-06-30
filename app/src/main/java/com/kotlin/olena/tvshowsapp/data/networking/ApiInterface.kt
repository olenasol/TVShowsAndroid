package com.kotlin.olena.tvshowsapp.data.networking

import androidx.lifecycle.LiveData
import com.kotlin.olena.tvshowsapp.data.models.Show
import com.kotlin.olena.tvshowsapp.data.models.ShowDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface{
    @GET("/shows")
    fun getShowsByPage(@Query("page") page:Int):LiveData<ApiResponse<List<Show>>>

    @GET("/shows/{showId}")
    fun getShowDetails(@Path("showId") showId:Int):LiveData<ApiResponse<ShowDetails>>

    @GET("/search/shows")
    fun searchShows(@Query("q") query:String):LiveData<ApiResponse<List<Show>>>
}