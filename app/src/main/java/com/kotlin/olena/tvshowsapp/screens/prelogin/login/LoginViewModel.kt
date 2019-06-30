package com.kotlin.olena.tvshowsapp.screens.prelogin.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.base.BaseViewModel
import com.kotlin.olena.tvshowsapp.base.TVShowsApplication
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.data.networking.Status
import com.kotlin.olena.tvshowsapp.screens.prelogin.PreloginViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(application: Application) : PreloginViewModel(application){

    fun loginEmail(email: String, password: String) {
        if (isInputValid(email,password)) {
            preloginState.postValue(Resource(Status.LOADING))
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,
                    password).addOnCompleteListener {task->
                if(task.isSuccessful){
                    preloginState.postValue(Resource(Status.SUCCESS))
                } else
                    preloginState.postValue(Resource(Status.ERROR,message = task.exception?.localizedMessage))
            }

        } else {
            preloginState.postValue(Resource(Status.ERROR,message = getApplication<TVShowsApplication>()
                    .applicationContext.getString(R.string.enter_data)))
        }
    }
}