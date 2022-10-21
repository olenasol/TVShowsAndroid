package com.kotlin.olena.tvshowsapp.domain.repository

import com.kotlin.olena.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun register(email: String, password: String): AuthResult

    suspend fun login(email: String, password: String): AuthResult

    fun authStateFlow(): Flow<Boolean>

    fun logout()
}