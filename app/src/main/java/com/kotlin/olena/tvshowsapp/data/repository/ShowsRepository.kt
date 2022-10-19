package com.kotlin.olena.tvshowsapp.data.repository


import com.kotlin.olena.network.RemoteShowDataSource
import com.kotlin.olena.tvshowsapp.data.db.ShowsDao
import com.kotlin.olena.tvshowsapp.domain.models.Show
import com.kotlin.olena.tvshowsapp.domain.models.ShowApiModel
import com.kotlin.olena.tvshowsapp.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(private val dao: ShowsDao,
                                              private val remoteShowDataSource: RemoteShowDataSource
): ShowRepository {
    override suspend fun retrieveListOfShows(): Flow<List<Show>> {
        //check if the db is not empty
        val shows = dao.getShows()
        if (shows.isEmpty()) {
            //retrieve shows from the api and save to db
            dao.insertShows(convertToDatabaseModel(remoteShowDataSource.retrieveShowsFromApi()))
            return flowOf(dao.getShows())

        } else {
            return flowOf(shows)
        }
    }

    override fun getShowById(id: Int): Show {
        return dao.getShowById(id)
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

