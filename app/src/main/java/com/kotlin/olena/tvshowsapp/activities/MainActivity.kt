package com.kotlin.olena.tvshowsapp.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.fragments.LoginViewModel
import com.kotlin.olena.tvshowsapp.fragments.show.list.ShowsListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var loginVM: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.main_container,
                    ShowsListFragment.newInstance()).commit()
        loginVM = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginVM?.getIsLoggedIn()?.observe(this, Observer<Boolean>{ isLogged->
            if(!isLogged!!){
                startActivity(Intent(this,StartActivity::class.java))
            }
        })
        navigationView.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {
        val count: Int = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id:Int = item.itemId
        when(id){
            R.id.nav_logout->loginVM?.logout()
        }
        return true
    }
}