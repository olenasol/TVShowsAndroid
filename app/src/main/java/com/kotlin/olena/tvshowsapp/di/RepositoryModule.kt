package com.kotlin.olena.tvshowsapp.di

import com.kotlin.olena.tvshowsapp.data.repository.LoginRepositoryImpl
import com.kotlin.olena.tvshowsapp.data.repository.ShowsRepositoryImpl
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import com.kotlin.olena.tvshowsapp.domain.repository.ShowRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindLoginRepository(
        repo: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun bindShowRepository(
        repo: ShowsRepositoryImpl
    ): ShowRepository
}