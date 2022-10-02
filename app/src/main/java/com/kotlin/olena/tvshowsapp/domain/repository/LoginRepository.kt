package com.kotlin.olena.tvshowsapp.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun register(email: String, password: String): Task<AuthResult>

    suspend fun login(email: String, password: String): Task<AuthResult>

    fun authStateFlow(): Flow<Boolean>

    fun logout()
}