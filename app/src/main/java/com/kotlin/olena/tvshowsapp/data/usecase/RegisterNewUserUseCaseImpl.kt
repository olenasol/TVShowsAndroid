package com.kotlin.olena.tvshowsapp.data.usecase

import com.kotlin.olena.tvshowsapp.domain.models.FirebaseAuthState
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RegisterNewUserUseCaseImpl @Inject constructor(
    private val repository: LoginRepository
): RegisterNewUserUseCase {

    override fun invoke(email: String, password: String): Flow<FirebaseAuthState> = flow {
        emit(FirebaseAuthState.Loading)
        val result = repository.register(email, password)
        emit( if (result.isSuccessful) FirebaseAuthState.Success else FirebaseAuthState.Error(result.exception?.localizedMessage))
    }
}