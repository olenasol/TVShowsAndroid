package com.kotlin.olena.tvshowsapp.base

import android.app.Application
import com.kotlin.olena.tvshowsapp.BuildConfig
import com.kotlin.olena.tvshowsapp.data.networking.AppExecutors
import com.kotlin.olena.tvshowsapp.di.ApplicationComponent
import com.kotlin.olena.tvshowsapp.di.DaggerApplicationComponent
import com.kotlin.olena.tvshowsapp.di.DaggerComponentProvider
import timber.log.Timber

class TVShowsApplication:Application(), DaggerComponentProvider {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppExecutors()
    }
    override val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
    }

}