package com.kotlin.olena.tvshowsapp.di

import com.kotlin.olena.tvshowsapp.data.repository.LoginRepositoryImpl
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindLoginRepository(
        useCase: LoginRepositoryImpl
    ): LoginRepository
}