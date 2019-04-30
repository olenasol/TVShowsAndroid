package com.kotlin.olena.tvshowsapp.base

import android.app.Application
import com.kotlin.olena.tvshowsapp.BuildConfig
import com.kotlin.olena.tvshowsapp.data.networking.AppExecutors
import timber.log.Timber

class TVShowsApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppExecutors()
    }
}