package com.kotlin.olena.tvshowsapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.di.injector
import com.kotlin.olena.tvshowsapp.presentation.prelogin.login.LoginFragment
import com.kotlin.olena.tvshowsapp.presentation.show.list.ShowsListFragment
import kotlinx.android.synthetic.main.layout_progress.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, injector.getMainViewModelFactory())[MainViewModel::class.java]
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authStateFlow.onEach { isLoggedIn ->
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction().replace(R.id.main_container,
                        if (isLoggedIn) ShowsListFragment.newInstance() else LoginFragment.newInstance()).commit()
                }.launchIn(this)
            }
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
}