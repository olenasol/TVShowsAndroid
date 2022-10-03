package com.kotlin.olena.tvshowsapp.domain.models


sealed class AuthState {
    object Loading: AuthState()
    class Error(val message: String?): AuthState()
    object Success: AuthState()
}

sealed class AuthResult {
    class Error(val localizedMessage: String?): AuthResult()
    object Success: AuthResult()
}
