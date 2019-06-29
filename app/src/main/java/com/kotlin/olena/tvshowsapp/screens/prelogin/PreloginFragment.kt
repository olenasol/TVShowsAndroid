package com.kotlin.olena.tvshowsapp.screens.prelogin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.base.BaseFragment
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.data.networking.Status

abstract class PreloginFragment<VM : PreloginViewModel>: BaseFragment<VM>(){

    protected lateinit var emailEdt: EditText
    protected lateinit var passwordEdt: EditText
    protected lateinit var submitBtn:Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailEdt = view.findViewById(R.id.emailEdt)
        passwordEdt = view.findViewById(R.id.passwordEdt)
        submitBtn = view.findViewById(R.id.submitBtn)
        subscribeToObservers()
    }

    protected fun handleStatus(resource: Resource<Void>){
        when(resource.status){
            Status.LOADING -> {
                emailEdt.clearFocus()
                passwordEdt.clearFocus()
                showLoading()
            }
            Status.SUCCESS -> {
                hideLoading()
                viewModel.clearData()
            }
            Status.ERROR ->{
                hideLoading()
                viewModel.clearData()
                showError(resource.message)
            }
        }
    }

    abstract fun subscribeToObservers()
}