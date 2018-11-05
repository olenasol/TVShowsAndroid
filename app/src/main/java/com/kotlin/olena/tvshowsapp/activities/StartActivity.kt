package com.kotlin.olena.tvshowsapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.fragments.login.LoginFragment

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            supportFragmentManager.beginTransaction().add(R.id.start_container,
                    LoginFragment.newInstance()).commit()
        }
    }
}
