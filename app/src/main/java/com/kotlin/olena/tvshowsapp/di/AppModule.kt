package com.kotlin.olena.tvshowsapp.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.data.db.AppDatabase
import com.kotlin.olena.tvshowsapp.data.db.ShowsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideShowDao(application: Application): ShowsDao {
        return AppDatabase.getDatabase(application).getShowsDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

}