package com.kotlin.olena.tvshowsapp.screens.prelogin.registration


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.data.networking.Status
import com.kotlin.olena.tvshowsapp.di.injector
import com.kotlin.olena.tvshowsapp.screens.prelogin.PreloginFragment
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : PreloginFragment<RegistrationViewModel>() {

    override fun provideViewModel(): RegistrationViewModel {
        return ViewModelProviders.of(this, activity?.injector?.getRegistrationViewModelFactory())
                .get(RegistrationViewModel::class.java)
    }

    companion object {
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    //region Observer
    val registerObserver = Observer<Resource<Void>?> { resource ->
        if (resource != null) {
            handleStatus(resource)
            if (resource.status == Status.LOADING) {
                confirmPasswordEdt.clearFocus()
            }
            if (resource.status == Status.SUCCESS) {
                fragmentManager?.popBackStack()
                showSuccess(getString(R.string.registered_successfully))
            }
        }
    }
    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submitBtn.setOnClickListener {
            viewModel.registerUser(emailEdt.text.toString(), passwordEdt.text.toString(),
                    confirmPasswordEdt.text.toString())
        }
    }

    override fun subscribeToObservers() {
        viewModel.getPreLoginState().observe(this.viewLifecycleOwner, registerObserver)
    }
}
