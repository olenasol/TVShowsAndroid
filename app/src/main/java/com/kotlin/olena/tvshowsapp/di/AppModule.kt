package com.kotlin.olena.tvshowsapp.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.data.db.AppDatabase
import com.kotlin.olena.tvshowsapp.data.db.ShowsDao
import com.kotlin.olena.tvshowsapp.data.networking.ApiInterface
import com.kotlin.olena.tvshowsapp.data.networking.LiveDataCallAdapterFactory
import com.kotlin.olena.tvshowsapp.other.BASE_URL
import com.kotlin.olena.tvshowsapp.other.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun provideShowDao(application: Application): ShowsDao {
        return AppDatabase.getDatabase(application).getShowsDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

}