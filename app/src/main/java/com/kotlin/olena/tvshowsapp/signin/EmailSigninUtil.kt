package com.kotlin.olena.tvshowsapp.signin

import android.app.Activity
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.callbacks.EmailLoginCallback

class EmailSigninUtil {

    companion object {

        fun newInstance(): EmailSigninUtil {
            return EmailSigninUtil()
        }
    }

    fun login(email: String, password: String,
              callback: EmailLoginCallback, activity: Activity) {
        if (!TextUtils.equals(email, "") && !TextUtils.equals(email, "")) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,
                    password).addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    callback.onLoginSuccess()
                } else {
                    callback.onLoginFailure()
                }
            }
        }
    }
}