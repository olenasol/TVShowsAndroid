package com.kotlin.olena.tvshowsapp.data.usecase

import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import com.kotlin.olena.tvshowsapp.domain.usecase.AuthStateUseCase
import javax.inject.Inject

class AuthStateUseCaseImpl @Inject constructor(
    private val repository: LoginRepository
): AuthStateUseCase {
    override fun getAuthStateFlow() = repository.authStateFlow()

}