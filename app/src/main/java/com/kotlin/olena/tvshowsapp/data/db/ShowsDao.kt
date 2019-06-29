package com.kotlin.olena.tvshowsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.olena.tvshowsapp.data.models.Show

@Dao
public interface ShowsDao{

    companion object{
        const val NUMBER_PER_PAGE =240
    }

    @Query("SELECT * FROM Show LIMIT 240 OFFSET (:page)*(240)")
    fun getShows(page:Int): LiveData<List<Show>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertShows(shows: List<Show>)
}