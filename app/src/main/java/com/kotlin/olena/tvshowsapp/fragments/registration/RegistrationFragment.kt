package com.kotlin.olena.tvshowsapp.fragments.registration


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
import com.kotlin.olena.tvshowsapp.fragments.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : BaseFragment<LoginViewModel>() {

    private var loginVM: LoginViewModel? = null

    companion object {
        fun newInstance(): RegistrationFragment {
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
                fragmentManager?.beginTransaction()?.replace(R.id.main_container,
                        LoginFragment.newInstance())?.commit()
            } else {
                Toast.makeText(activity, R.string.register_failed,
                        Toast.LENGTH_SHORT).show()
            }
        })
        registerBtn.setOnClickListener {
            loginVM?.registerUser(emailRegistrationEdt.text.toString(), passwordRegistrationEdt.text.toString(),
                    confirmPasswordEdt.text.toString(), activity!!)
        }
    }
}
