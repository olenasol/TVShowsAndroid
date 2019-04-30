package com.kotlin.olena.tvshowsapp.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import com.kotlin.olena.tvshowsapp.data.models.Show


@Database(entities = [Show::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getShowsDao(): ShowsDao

    companion object {

        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "shows_db")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance!!
        }
    }

}