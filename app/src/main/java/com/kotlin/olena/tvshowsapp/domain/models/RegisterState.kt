package com.kotlin.olena.tvshowsapp.domain.models


sealed class RegisterState {
    object Loading: RegisterState()
    class Error(val message: String?): RegisterState()
    object Success: RegisterState()
}
