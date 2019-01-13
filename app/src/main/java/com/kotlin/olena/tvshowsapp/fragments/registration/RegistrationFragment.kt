package com.kotlin.olena.tvshowsapp.fragments.registration


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.fragments.LoginViewModel
import com.kotlin.olena.tvshowsapp.fragments.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_registration.*
import org.jetbrains.anko.sdk21.coroutines.onClick

class RegistrationFragment : Fragment() {

    var loginVM: LoginViewModel? = null

    companion object {
        fun newInstance():RegistrationFragment{
            return RegistrationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginVM = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        loginVM?.getIsRegistered()?.observe(this, Observer<Boolean> { isRegistered ->
                if (isRegistered!!) {
                    fragmentManager?.beginTransaction()?.replace(R.id.start_container,
                            LoginFragment.newInstance())?.commit()
                } else{
                    Toast.makeText(activity, R.string.register_failed,
                            Toast.LENGTH_SHORT).show()
                }
        })
        registerBtn.setOnClickListener {
            loginVM?.registerUser(emailRegistrationEdt.text.toString(),passwordRegistrationEdt.text.toString(),
                    confirmPasswordEdt.text.toString(),activity!!)
        }
    }
}
