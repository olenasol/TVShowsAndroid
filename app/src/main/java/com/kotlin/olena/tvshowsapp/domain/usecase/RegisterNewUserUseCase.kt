package com.kotlin.olena.tvshowsapp.domain.usecase

import com.kotlin.olena.tvshowsapp.domain.models.AuthState
import kotlinx.coroutines.flow.Flow

interface RegisterNewUserUseCase {
    fun invoke(email:String, password:String): Flow<AuthState>
}