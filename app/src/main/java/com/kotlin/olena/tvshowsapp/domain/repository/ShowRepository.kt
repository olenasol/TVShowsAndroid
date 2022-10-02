package com.kotlin.olena.tvshowsapp.domain.repository

import com.kotlin.olena.tvshowsapp.domain.models.Show
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    suspend fun retrieveListOfShows(): Flow<List<Show>>
}