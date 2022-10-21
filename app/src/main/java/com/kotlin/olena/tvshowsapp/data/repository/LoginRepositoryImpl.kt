package com.kotlin.olena.tvshowsapp.data.repository

import com.kotlin.olena.auth.AuthDataSource
import com.kotlin.olena.auth.AuthResult
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource): LoginRepository {

    override suspend fun register(email: String, password: String): AuthResult = authDataSource.register(email, password)

    override suspend fun login(email: String, password: String): AuthResult = authDataSource.login(email, password)

    override fun authStateFlow(): Flow<Boolean> = authDataSource.authStateFlow()

    override fun logout() {
        authDataSource.logout()
    }


}