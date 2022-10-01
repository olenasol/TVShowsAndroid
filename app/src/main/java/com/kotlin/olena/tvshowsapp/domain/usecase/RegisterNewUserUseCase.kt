package com.kotlin.olena.tvshowsapp.domain.usecase

import com.kotlin.olena.tvshowsapp.domain.models.FirebaseAuthState
import kotlinx.coroutines.flow.Flow

interface RegisterNewUserUseCase {
    fun invoke(email:String, password:String): Flow<FirebaseAuthState>
}