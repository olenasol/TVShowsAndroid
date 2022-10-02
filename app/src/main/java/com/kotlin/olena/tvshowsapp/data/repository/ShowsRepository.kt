package com.kotlin.olena.tvshowsapp.data.repository


import com.kotlin.olena.tvshowsapp.data.db.ShowsDao
import com.kotlin.olena.tvshowsapp.data.networking.*
import com.kotlin.olena.tvshowsapp.domain.models.Show
import com.kotlin.olena.tvshowsapp.domain.models.ShowApiModel
import com.kotlin.olena.tvshowsapp.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ShowsRepositoryImpl @Inject constructor(private val dao: ShowsDao,
                                              private val api: ApiInterface): ShowRepository {
    override suspend fun retrieveListOfShows(): Flow<List<Show>> {
        //check if the db is not empty
        val shows = dao.getShows()
        if (shows.isEmpty()) {
            //retrieve shows from the api and save to db
            dao.insertShows(convertToDatabaseModel(retrieveShowsFromApi()))
            return flowOf(dao.getShows())

        } else {
            return flowOf(shows)
        }
    }

    private suspend fun retrieveShowsFromApi() = suspendCoroutine<List<ShowApiModel>> { continuation ->
        api.getShows().enqueue(object : retrofit2.Callback<List<ShowApiModel>> {
            override fun onResponse(
                call: Call<List<ShowApiModel>>,
                response: Response<List<ShowApiModel>>
            ) {
                if (response.isSuccessful) {
                    continuation.resume(response.body().orEmpty())
                }
            }

            override fun onFailure(call: Call<List<ShowApiModel>>, t: Throwable) {
                continuation.resume(listOf())
            }

        })
    }

    private fun convertToDatabaseModel(shows: List<ShowApiModel>) = shows.map { show ->
        Show(
            id = show.id,
            name = show.name,
            imageUrl = show.image?.originalImageUrl,
            rating = show.rating?.rating,
            status = show.status,
            officialSite = show.officialSite,
            language = show.language
        )
    }
}

