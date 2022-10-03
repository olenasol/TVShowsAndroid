package com.kotlin.olena.tvshowsapp.data.usecase

import com.kotlin.olena.tvshowsapp.domain.models.AuthResult
import com.kotlin.olena.tvshowsapp.domain.models.AuthState
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import com.kotlin.olena.tvshowsapp.domain.usecase.LoginUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCaseImpl @Inject constructor(
    private val repository: LoginRepository
): LoginUserUseCase {
    override suspend fun invoke(email: String, password: String): Flow<AuthState> = flow {
        emit(AuthState.Loading)
        val authResult = repository.login(email, password)
        emit(when(authResult) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Error -> AuthState.Error(authResult.localizedMessage)
        })
    }
}