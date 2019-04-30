package com.kotlin.olena.tvshowsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.olena.tvshowsapp.data.models.Show

@Dao
public interface ShowsDao{
    @Query("select * from Show")
    fun getShows(): LiveData<List<Show>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertShows(shows: List<Show>)
}