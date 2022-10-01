package com.kotlin.olena.tvshowsapp.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.domain.repository.LoginRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class LoginRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth): LoginRepository {
    override suspend fun register(email: String, password: String): Task<AuthResult> {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {result ->
                continuation.resume(result)
            }
        }
    }
}