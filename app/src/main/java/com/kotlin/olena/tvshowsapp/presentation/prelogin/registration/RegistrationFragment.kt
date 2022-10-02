package com.kotlin.olena.tvshowsapp.presentation.prelogin.registration


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.databinding.FragmentRegistrationBinding
import com.kotlin.olena.tvshowsapp.di.injector
import com.kotlin.olena.tvshowsapp.domain.models.FieldInputState
import com.kotlin.olena.tvshowsapp.domain.models.InputField
import com.kotlin.olena.tvshowsapp.domain.models.FirebaseAuthState
import com.kotlin.olena.tvshowsapp.other.InputValidationHelper.getErrorInputMessage
import com.kotlin.olena.tvshowsapp.other.showErrorSnackBar
import com.kotlin.olena.tvshowsapp.other.showSuccessSnackBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val viewModel: RegistrationViewModel by viewModels {
        activity?.injector?.getRegistrationViewModelFactory() as ViewModelProvider.Factory
    }

    private var _binding: FragmentRegistrationBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistrationBinding.bind(view)
        subscribeToObservers()
        _binding?.submitBtn?.setOnClickListener {
            viewModel.registerUser(
                _binding?.editTextEmail?.text?.toString(),
                _binding?.editTextPassword?.text?.toString(),
                _binding?.editTextConfirmPassword?.text?.toString()
            )
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
                viewModel.registerState.onEach { registerState ->
                    updateRegisterState(registerState)
                }.launchIn(this)
            }
        }
    }

    private fun updateRegisterState(registerState: FirebaseAuthState) {
        when (registerState) {
            is FirebaseAuthState.Loading -> {
                _binding?.submitBtn?.visibility = View.GONE
                _binding?.loader?.visibility = View.VISIBLE
            }
            is FirebaseAuthState.Success -> {
                showSuccessSnackBar(getString(R.string.success))
            }
            is FirebaseAuthState.Error -> {
                _binding?.submitBtn?.visibility = View.VISIBLE
                _binding?.loader?.visibility = View.GONE
                showErrorSnackBar(registerState.message.orEmpty())
            }
        }
    }

    private fun updateInputFieldsState(inputStateMap: Map<InputField, FieldInputState>) {
        _binding?.inputEmail?.error = getErrorInputMessage(context, inputStateMap, InputField.EMAIL)
        _binding?.inputPassword?.error = getErrorInputMessage(context, inputStateMap, InputField.PASSWORD)
        _binding?.inputConfirmPassword?.error = getErrorInputMessage(context,inputStateMap, InputField.CONFIRM_PASSWORD)
    }

    companion object {
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }
}
