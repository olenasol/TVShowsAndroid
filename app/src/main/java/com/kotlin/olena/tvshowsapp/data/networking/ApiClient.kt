package com.kotlin.olena.tvshowsapp.data.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private const val BASE_URL = "http://api.tvmaze.com"


        fun getClient(): Retrofit {
            val builder = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
            return builder.build()
        }
    }
}