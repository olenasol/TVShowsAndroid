package com.kotlin.olena.auth

sealed class AuthResult {
    class Error(val localizedMessage: String?): AuthResult()
    object Success: AuthResult()
}