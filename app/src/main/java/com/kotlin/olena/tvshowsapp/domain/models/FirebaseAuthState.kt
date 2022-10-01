package com.kotlin.olena.tvshowsapp.domain.models


sealed class FirebaseAuthState {
    object Loading: FirebaseAuthState()
    class Error(val message: String?): FirebaseAuthState()
    object Success: FirebaseAuthState()
}
