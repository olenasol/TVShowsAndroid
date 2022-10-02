package com.kotlin.olena.tvshowsapp.di

import com.kotlin.olena.tvshowsapp.data.usecase.AuthStateUseCaseImpl
import com.kotlin.olena.tvshowsapp.data.usecase.LoginUserUseCaseImpl
import com.kotlin.olena.tvshowsapp.data.usecase.RegisterNewUserUseCaseImpl
import com.kotlin.olena.tvshowsapp.domain.usecase.AuthStateUseCase
import com.kotlin.olena.tvshowsapp.domain.usecase.LoginUserUseCase
import com.kotlin.olena.tvshowsapp.domain.usecase.LogoutUseCase
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun bindRegisterNewUserUseCase(
        useCase: RegisterNewUserUseCaseImpl
    ): RegisterNewUserUseCase

    @Binds
    abstract fun bindLoginUserUseCase(
        useCase:LoginUserUseCaseImpl
    ): LoginUserUseCase

    @Binds
    abstract fun bindAuthStateUseCase(
        useCase:AuthStateUseCaseImpl
    ):AuthStateUseCase

    @Binds
    abstract fun bindLogoutUseCase(
        useCase: LoginUserUseCaseImpl
    ): LogoutUseCase
}