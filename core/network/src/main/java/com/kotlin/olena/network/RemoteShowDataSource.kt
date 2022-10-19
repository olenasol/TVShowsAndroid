package com.kotlin.olena.network

import com.kotlin.olena.tvshowsapp.domain.models.ShowApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteShowDataSource @Inject constructor(
    private val api: ApiInterface
) {

    suspend fun retrieveShowsFromApi() = suspendCoroutine<List<ShowApiModel>> { continuation ->
        api.getShows().enqueue(object : Callback<List<ShowApiModel>> {
            override fun onResponse(
                call: Call<List<ShowApiModel>>,
                response: Response<List<ShowApiModel>>
            ) {
                if (response.isSuccessful) {
                    continuation.resume(response.body().orEmpty())
                }
            }

            override fun onFailure(call: Call<List<ShowApiModel>>, trowable: Throwable) {
                continuation.resume(listOf())
            }

        })
    }
}