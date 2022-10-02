package com.kotlin.olena.tvshowsapp.di

import com.kotlin.olena.tvshowsapp.data.usecase.*
import com.kotlin.olena.tvshowsapp.domain.usecase.*
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
        useCase: LogoutUseCaseImpl
    ): LogoutUseCase

    @Binds
    abstract fun bindGetShowListUseCase(
        userCase: GetShowListUseCaseImpl
    ): GetShowListUseCase

    @Binds
    abstract fun bindGetShowById(
        useCase: GetShowByIdImpl
    ): GetShowById
}