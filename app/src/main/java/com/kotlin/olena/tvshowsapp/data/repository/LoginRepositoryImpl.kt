package com.kotlin.olena.tvshowsapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.domain.models.AuthResult
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class LoginRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth): LoginRepository {

    override suspend fun register(email: String, password: String): AuthResult {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {result ->
                continuation.resume(if (result.isSuccessful) AuthResult.Success else AuthResult.Error(result.exception?.localizedMessage))
            }
        }
    }

    override suspend fun login(email: String, password: String): AuthResult {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {result ->
                continuation.resume(if (result.isSuccessful) AuthResult.Success else AuthResult.Error(result.exception?.localizedMessage))
            }
        }
    }

    override fun authStateFlow(): Flow<Boolean> = callbackFlow {

        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser != null)
        }

        firebaseAuth.addAuthStateListener(listener)

        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }


}