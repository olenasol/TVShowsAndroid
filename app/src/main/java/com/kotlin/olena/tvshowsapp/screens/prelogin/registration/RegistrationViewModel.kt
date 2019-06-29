package com.kotlin.olena.tvshowsapp.screens.prelogin.registration

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.base.TVShowsApplication
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.data.networking.Status
import com.kotlin.olena.tvshowsapp.screens.prelogin.PreloginViewModel

class RegistrationViewModel(application: Application) : PreloginViewModel(application) {

    fun registerUser(email:String,password:String,confirmPassword:String){
        if (isInputValid(email,password,confirmPassword)) {
            if (arePasswordsTheSame(password,confirmPassword)) {
                preloginState.postValue(Resource(Status.LOADING))
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,
                        password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        preloginState.postValue(Resource(Status.SUCCESS))
                    } else
                        preloginState.postValue(Resource(Status.ERROR, message = task.exception?.localizedMessage))
                }
            } else{
                preloginState.postValue(Resource(Status.ERROR,message = getApplication<TVShowsApplication>()
                        .applicationContext.getString(R.string.passwords_different)))
            }

        } else {
            preloginState.postValue(Resource(Status.ERROR,message = getApplication<TVShowsApplication>()
                    .applicationContext.getString(R.string.enter_data)))
        }
    }
    private fun arePasswordsTheSame(password: String,confirmPassword: String):Boolean{
        return password == confirmPassword
    }
}