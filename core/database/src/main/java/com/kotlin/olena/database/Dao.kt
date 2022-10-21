package com.kotlin.olena.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShowsDao{

    @Query("SELECT * FROM Show")
    fun getShows(): List<Show>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(shows: List<Show>)

    @Query("SELECT * FROM Show WHERE id = :id")
    fun getShowById(id: Int): Show
}
