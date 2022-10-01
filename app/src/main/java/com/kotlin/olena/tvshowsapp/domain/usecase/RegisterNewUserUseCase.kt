package com.kotlin.olena.tvshowsapp.domain.usecase

import com.kotlin.olena.tvshowsapp.domain.models.RegisterState
import kotlinx.coroutines.flow.Flow

interface RegisterNewUserUseCase {
    fun registerUser(email:String, password:String): Flow<RegisterState>
}