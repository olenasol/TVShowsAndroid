package com.kotlin.olena.tvshowsapp.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient() {

    companion object {
        private val BASE_URL = "http://api.tvmaze.com"

        fun getClient(): Retrofit {
            val builder = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
            return builder.build()
        }
    }
}