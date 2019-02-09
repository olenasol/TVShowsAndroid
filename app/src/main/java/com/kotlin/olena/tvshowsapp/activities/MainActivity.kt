package com.kotlin.olena.tvshowsapp.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.fragments.shared.LoginViewModel
import com.kotlin.olena.tvshowsapp.fragments.login.LoginFragment
import com.kotlin.olena.tvshowsapp.fragments.show.list.ShowsListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var loginVM: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginVM = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginVM?.getIsLoggedIn()?.observe(this, Observer<Boolean> { isLogged ->
            if (!isLogged!!) {
                supportFragmentManager.beginTransaction().replace(R.id.main_container,
                        LoginFragment.newInstance()).commit()
            } else{
                if (savedInstanceState == null)
                    supportFragmentManager.beginTransaction().replace(R.id.main_container,
                            ShowsListFragment.newInstance()).commit()
            }
        })
    }

    override fun onBackPressed() {
        val count: Int = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}