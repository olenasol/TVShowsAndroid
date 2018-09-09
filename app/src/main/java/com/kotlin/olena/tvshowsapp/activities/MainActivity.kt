package com.kotlin.olena.tvshowsapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.olena.tvshowsapp.fragments.show.list.ShowsListFragment
import com.kotlin.olena.tvshowsapp.ui.layout.MainActivityUI
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(MainActivityUI.mainLayoutId,
                    ShowsListFragment.newInstance()).commit()
    }
    override fun onBackPressed() {
        val count:Int = supportFragmentManager.backStackEntryCount
        if(count==0){
            super.onBackPressed()
        } else{
            supportFragmentManager.popBackStack()
        }
    }
}