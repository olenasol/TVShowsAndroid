package com.kotlin.olena.tvshowsapp.di

import com.kotlin.olena.tvshowsapp.other.DefaultCoroutineContextProvider
import com.kotlin.olena.tvshowsapp.other.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module
abstract class CoroutineModule {

    @Binds
    abstract fun bindCoroutineContextProvider(
        provider: DefaultCoroutineContextProvider
    ): DispatcherProvider
}