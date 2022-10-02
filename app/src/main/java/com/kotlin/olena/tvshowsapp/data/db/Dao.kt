package com.kotlin.olena.tvshowsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.olena.tvshowsapp.domain.models.Show
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowsDao{

    @Query("SELECT * FROM Show")
    fun getShows(): List<Show>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(shows: List<Show>)

    @Query("SELECT * FROM Show WHERE id = :id")
    fun getShowById(id: Int): Show
}
