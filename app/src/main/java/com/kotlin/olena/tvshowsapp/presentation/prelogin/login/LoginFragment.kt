package com.kotlin.olena.tvshowsapp.presentation.prelogin.login


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.databinding.FragmentLoginBinding
import com.kotlin.olena.tvshowsapp.di.injector
import com.kotlin.olena.tvshowsapp.domain.models.FieldInputState
import com.kotlin.olena.tvshowsapp.domain.models.FirebaseAuthState
import com.kotlin.olena.tvshowsapp.domain.models.InputField
import com.kotlin.olena.tvshowsapp.other.InputValidationHelper.getErrorInputMessage
import com.kotlin.olena.tvshowsapp.other.replaceFragment
import com.kotlin.olena.tvshowsapp.other.showErrorSnackBar
import com.kotlin.olena.tvshowsapp.presentation.prelogin.registration.RegistrationFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels {
        activity?.injector?.getLoginViewModelFactory() as ViewModelProvider.Factory
    }

    private var _binding: FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        subscribeToObservers()
        _binding?.submitBtn?.setOnClickListener {
            viewModel.loginUser(
                _binding?.editTextEmail?.text?.toString(),
                _binding?.editTextPassword?.text?.toString()
            )
        }
        _binding?.btnRegister?.setOnClickListener {
            replaceFragment(RegistrationFragment.newInstance(), R.id.main_container,true)
        }
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        _binding = null
        super.onDestroyView()
    }


    private fun subscribeToObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.inputFieldsState.onEach { inputFieldsData ->
                    updateInputFieldsState(inputFieldsData)
                }.launchIn(this)
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.onEach { registerState ->
                    updateLoginState(registerState)
                }.launchIn(this)
            }
        }
    }

    private fun updateLoginState(loginState: FirebaseAuthState) {
        when (loginState) {
            is FirebaseAuthState.Loading -> {
                _binding?.submitBtn?.visibility = View.GONE
                _binding?.loader?.visibility = View.VISIBLE
            }
            is FirebaseAuthState.Error -> {
                _binding?.submitBtn?.visibility = View.VISIBLE
                _binding?.loader?.visibility = View.GONE
                showErrorSnackBar(loginState.message.orEmpty())
            }
            else -> {}
        }
    }

    private fun updateInputFieldsState(inputStateMap: Map<InputField, FieldInputState>) {
        _binding?.inputEmail?.error = getErrorInputMessage(context,inputStateMap, InputField.EMAIL)
        _binding?.inputPassword?.error = getErrorInputMessage(context,inputStateMap, InputField.PASSWORD)
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
