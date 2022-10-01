package com.kotlin.olena.tvshowsapp.di

import com.kotlin.olena.tvshowsapp.data.usecase.RegisterNewUserUseCaseImpl
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun bindRegisterNewUserUseCase(
        useCase: RegisterNewUserUseCaseImpl
    ): RegisterNewUserUseCase

}