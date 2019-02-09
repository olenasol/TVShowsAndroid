package com.kotlin.olena.tvshowsapp.fragments.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.fragments.base.BaseFragment
import com.kotlin.olena.tvshowsapp.fragments.shared.LoginViewModel
import com.kotlin.olena.tvshowsapp.fragments.registration.RegistrationFragment
import com.kotlin.olena.tvshowsapp.fragments.show.list.ShowsListFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment<LoginViewModel>(){

    private var loginVM: LoginViewModel? = null

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
        loginVM?.getIsLoggedIn()?.observe(this, Observer<Boolean> { isLogged ->
            if (isLogged!!) {
                fragmentManager?.beginTransaction()?.replace(R.id.main_container, ShowsListFragment.newInstance())?.commit()
            } else {
                Toast.makeText(activity, R.string.auth_failed,
                        Toast.LENGTH_SHORT).show()
            }
        })
        loginBtn.setOnClickListener {
            loginVM?.loginEmail(emailEdt.text.toString(), passwordEdt.text.toString(), activity!!)
        }
        registerTxt.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_container, RegistrationFragment.newInstance())?.addToBackStack(RegistrationFragment::class.java.simpleName)?.commit()
        }
    }


}
