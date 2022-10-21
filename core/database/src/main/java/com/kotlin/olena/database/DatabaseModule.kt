package com.kotlin.olena.database

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideShowDao(application: Application): ShowsDao {
        return AppDatabase.getDatabase(application).getShowsDao()
    }
}