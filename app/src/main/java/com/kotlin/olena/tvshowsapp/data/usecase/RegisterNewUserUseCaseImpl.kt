package com.kotlin.olena.tvshowsapp.data.usecase

import com.kotlin.olena.tvshowsapp.domain.models.RegisterState
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RegisterNewUserUseCaseImpl @Inject constructor(
    private val repository: LoginRepository
): RegisterNewUserUseCase {

    override fun registerUser(email: String, password: String): Flow<RegisterState> = flow {
        emit(RegisterState.Loading)
        val result = repository.register(email, password)
        emit( if (result.isSuccessful) RegisterState.Success else RegisterState.Error(result.exception?.localizedMessage))
    }
}