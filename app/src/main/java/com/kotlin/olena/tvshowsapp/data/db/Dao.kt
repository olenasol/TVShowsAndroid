package com.kotlin.olena.tvshowsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.olena.tvshowsapp.data.models.Show
import com.kotlin.olena.tvshowsapp.data.models.ShowDetails

@Dao
interface ShowsDao{

    companion object{
        const val NUMBER_PER_PAGE =240
    }
    //TODO get rid of hardcoded number of elements
    @Query("SELECT * FROM Show LIMIT 240 OFFSET (:page)*(240)")
    fun getShows(page:Int): LiveData<List<Show>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(shows: List<Show>)
}

@Dao
interface  ShowDetailsDao{

    @Query("SELECT * FROM ShowDetails WHERE id = (:id)")
    fun getShowDetail(id:Int): LiveData<ShowDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShowDetail(shows: ShowDetails)
}