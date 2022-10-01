package com.kotlin.olena.tvshowsapp.screens.prelogin

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.olena.tvshowsapp.base.BaseViewModel
import com.kotlin.olena.tvshowsapp.data.networking.Resource

open class PreloginViewModel(application: Application) : BaseViewModel(application){

    protected val preloginState: MutableLiveData<Resource<Void>?> = MutableLiveData()

    fun getPreLoginState() = preloginState as LiveData<Resource<Void>?>

    protected fun isInputValid(email: String?, password: String?,passwordConfirm: String?=null) : Boolean {
        val isPasswordEmailValid = (email?.isNotBlank() ?:false) && (password?.isNotBlank() ?: false)
        if (passwordConfirm != null){
            return isPasswordEmailValid && passwordConfirm.isNotBlank()
        }
        return isPasswordEmailValid
    }

    fun clearData(){
        preloginState.value = null
    }
}