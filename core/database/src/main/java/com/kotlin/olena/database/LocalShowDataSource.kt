package com.kotlin.olena.database

import javax.inject.Inject

class LocalShowDataSource @Inject constructor(
    private val dao: ShowsDao
) {
    fun getShows(): List<Show> = dao.getShows()

    fun insertShows(shows: List<Show>) {
        dao.insertShows(shows)
    }

    fun getShowById(id: Int): Show = dao.getShowById(id)
}