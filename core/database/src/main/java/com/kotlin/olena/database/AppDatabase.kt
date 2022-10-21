package com.kotlin.olena.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database


@Database(entities = [Show::class], version = 1)
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