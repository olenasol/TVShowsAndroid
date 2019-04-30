package com.kotlin.olena.tvshowsapp.fragments.shared

import android.app.Activity
import android.app.Application
import androidx.lifecycle.MutableLiveData
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.base.BaseViewModel

class LoginViewModel(application: Application) : BaseViewModel(application){

    private var isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    private var isRegistered:MutableLiveData<Boolean> = MutableLiveData()

    init {
        isLoggedIn.postValue(FirebaseAuth.getInstance().currentUser != null)
    }

    public fun loginEmail(email: String, password: String,
              activity: Activity) {
        if (!TextUtils.equals(email, "") && !TextUtils.equals(password, "")) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,
                    password).addOnCompleteListener(activity) { task ->
                isLoggedIn.postValue(task.isSuccessful)
            }
        }
    }

    public fun getIsLoggedIn():MutableLiveData<Boolean>{
        return isLoggedIn
    }

    public fun registerUser(email:String,password:String,confirmPassword:String,activity: Activity){
        if(!TextUtils.equals(password,confirmPassword)&&!TextUtils.equals(email, "") &&
                !TextUtils.equals(password, "")){
            isRegistered.postValue(false)
        } else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity){
                task ->
                isRegistered.postValue(task.isSuccessful)
            }
        }
    }

    public fun getIsRegistered():MutableLiveData<Boolean>{
        return isRegistered
    }

    public fun logout(){
        FirebaseAuth.getInstance().signOut()
        isLoggedIn.value = false
    }

}