package com.kotlin.olena.tvshowsapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.fragments.shows_list.ShowsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.mainLayout,
                ShowsListFragment.newInstance()).commit()
    }
}