package com.kotlin.olena.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
){

    suspend fun register(email: String, password: String): AuthResult {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {result ->
                continuation.resume(if (result.isSuccessful) AuthResult.Success else AuthResult.Error(result.exception?.localizedMessage))
            }
        }
    }

    suspend fun login(email: String, password: String): AuthResult {
        return suspendCoroutine { continuation ->
            continuation.resume(AuthResult.Success)
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {result ->
                continuation.resume(if (result.isSuccessful) AuthResult.Success else AuthResult.Error(result.exception?.localizedMessage))
            }
        }
    }

    fun authStateFlow(): Flow<Boolean> = callbackFlow {

        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser != null)
        }

        firebaseAuth.addAuthStateListener(listener)

        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}