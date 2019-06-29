package com.kotlin.olena.tvshowsapp.screens.prelogin.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.data.networking.Status
import com.kotlin.olena.tvshowsapp.other.replaceFragment
import com.kotlin.olena.tvshowsapp.screens.prelogin.PreloginFragment
import com.kotlin.olena.tvshowsapp.screens.prelogin.registration.RegistrationFragment
import com.kotlin.olena.tvshowsapp.screens.show.list.ShowsListFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : PreloginFragment<LoginViewModel>() {

    override fun provideViewModel(): LoginViewModel {
        return ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    //region Observer
    val loginObserver = Observer<Resource<Void>?> { resource ->
        if (resource != null) {
            handleStatus(resource)
            if (resource.status == Status.SUCCESS) {
                replaceFragment(ShowsListFragment.newInstance(), R.id.main_container)
            }
        }
    }
    //endregion

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
        submitBtn.setOnClickListener {
            viewModel.loginEmail(emailEdt.text.toString(), passwordEdt.text.toString())
        }
        registerTxt.setOnClickListener {
            replaceFragment(RegistrationFragment.newInstance(), R.id.main_container,true)
        }
    }

    override fun subscribeToObservers() {
        viewModel.getPreLoginState().observe(this.viewLifecycleOwner, loginObserver)
    }
}
