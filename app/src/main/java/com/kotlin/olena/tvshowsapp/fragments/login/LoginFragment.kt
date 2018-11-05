package com.kotlin.olena.tvshowsapp.fragments.login


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.activities.MainActivity
import com.kotlin.olena.tvshowsapp.callbacks.EmailLoginCallback
import com.kotlin.olena.tvshowsapp.fragments.LoginViewModel
import com.kotlin.olena.tvshowsapp.fragments.registration.RegistrationFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import org.jetbrains.anko.sdk21.coroutines.onClick


class LoginFragment : Fragment() {

    var loginVM: LoginViewModel? = null

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginVM = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        loginVM?.getIsLoggedIn()?.observe(this, Observer<Boolean>{ isLogged->
            if(isLogged!!){
                startActivity(Intent(activity, MainActivity::class.java))
            }else{
                Toast.makeText(activity, R.string.auth_failed,
                        Toast.LENGTH_SHORT).show()
            }
        })
        loginBtn.onClick {
            loginVM?.loginEmail(emailEdt.text.toString(), passwordEdt.text.toString(),activity!!)
        }
        registerTxt.onClick {
            fragmentManager?.beginTransaction()?.replace(R.id.start_container,RegistrationFragment.newInstance())?.commit()
        }
    }


}
