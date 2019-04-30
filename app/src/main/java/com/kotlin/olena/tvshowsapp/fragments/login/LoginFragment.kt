package com.kotlin.olena.tvshowsapp.fragments.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.base.BaseFragment
import com.kotlin.olena.tvshowsapp.fragments.shared.LoginViewModel
import com.kotlin.olena.tvshowsapp.fragments.registration.RegistrationFragment
import com.kotlin.olena.tvshowsapp.fragments.show.list.ShowsListFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment<LoginViewModel>(){

    override fun provideViewModel(): LoginViewModel {
        return ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
    }

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
        viewModel.getIsLoggedIn().observe(this, Observer<Boolean> { isLogged ->
            if (isLogged!!) {
                replaceFragment(ShowsListFragment.newInstance())
            } else {
                Toast.makeText(activity, R.string.auth_failed,
                        Toast.LENGTH_SHORT).show()
            }
        })
        loginBtn.setOnClickListener {
            viewModel.loginEmail(emailEdt.text.toString(), passwordEdt.text.toString(), activity!!)
        }
        registerTxt.setOnClickListener {
            replaceFragment(RegistrationFragment.newInstance(),true)
        }
    }


}
