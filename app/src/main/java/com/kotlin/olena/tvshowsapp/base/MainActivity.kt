package com.kotlin.olena.tvshowsapp.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.screens.prelogin.login.LoginFragment
import com.kotlin.olena.tvshowsapp.screens.show.list.ShowsListFragment
import kotlinx.android.synthetic.main.layout_progress.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            if (FirebaseAuth.getInstance().currentUser == null) {
                supportFragmentManager.beginTransaction().replace(R.id.main_container,
                        LoginFragment.newInstance()).commit()
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.main_container,
                        ShowsListFragment.newInstance()).commit()
            }
    }

    override fun onBackPressed() {
        val count: Int = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    fun changeLoadingState(isVisible: Boolean) {
        layoutProgressBar.visibility =
                if (isVisible) View.VISIBLE
                else View.GONE
    }
}