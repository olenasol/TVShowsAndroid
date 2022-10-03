package com.kotlin.olena.tvshowsapp.data.usecase

import com.kotlin.olena.tvshowsapp.domain.models.AuthResult
import com.kotlin.olena.tvshowsapp.domain.models.AuthState
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RegisterNewUserUseCaseImpl @Inject constructor(
    private val repository: LoginRepository
): RegisterNewUserUseCase {

    override fun invoke(email: String, password: String): Flow<AuthState> = flow {
        emit(AuthState.Loading)
        val authResult = repository.register(email, password)
        emit( when(authResult) {
            is AuthResult.Success -> AuthState.Success
            is AuthResult.Error -> AuthState.Error(authResult.localizedMessage)
        })
    }
}