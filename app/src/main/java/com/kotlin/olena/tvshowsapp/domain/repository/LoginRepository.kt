package com.kotlin.olena.tvshowsapp.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface LoginRepository {
    suspend fun register(email: String, password: String): Task<AuthResult>
}