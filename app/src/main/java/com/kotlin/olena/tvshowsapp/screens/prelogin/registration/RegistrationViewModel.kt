package com.kotlin.olena.tvshowsapp.screens.prelogin.registration

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.domain.models.RegisterState
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import com.kotlin.olena.tvshowsapp.other.MIN_PASSWORD_LENGTH
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerNewUserUseCase: RegisterNewUserUseCase
) : ViewModel() {

    private val _inputFieldsState = MutableStateFlow(InputState(
        email = FieldInputState.Valid,
        password = FieldInputState.Valid,
        confirmPassword = FieldInputState.Valid
    ))
    val inputFieldsState = _inputFieldsState.asStateFlow()

    private val _registerState = MutableSharedFlow<RegisterState>()
    val registerState:Flow<RegisterState> = _registerState

    fun registerUser(email:String?, password:String?, confirmPassword:String?){
        viewModelScope.launch {
            _registerState.emit(RegisterState.Loading)
            val inputState = InputState(
                email = getEmailState(email),
                password = getPasswordState(password),
                confirmPassword = getConfirmPasswordState(password, confirmPassword)
            )
            _inputFieldsState.emit(inputState)
            if (areAllFieldsValid(inputState)) {
                if (email != null && password != null) {
                    _registerState.emitAll(registerNewUserUseCase.registerUser(email, password))
                }
            }
        }
    }

    private fun areAllFieldsValid(inputState: InputState) = inputState.email == FieldInputState.Valid &&
            inputState.password == FieldInputState.Valid &&
            inputState.confirmPassword == FieldInputState.Valid

    private fun getEmailState(email: String?): FieldInputState {
        return when {
            email == null -> FieldInputState.Invalid(R.string.empty_input)
            email.isBlank() -> FieldInputState.Invalid(R.string.empty_input)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->FieldInputState.Invalid(R.string.incorrect_email_format)
            else -> FieldInputState.Valid
        }
    }

    private fun getPasswordState(password: String?): FieldInputState {
        return when {
            password == null -> FieldInputState.Invalid(R.string.empty_input)
            password.isBlank() -> FieldInputState.Invalid(R.string.empty_input)
            password.length < MIN_PASSWORD_LENGTH -> FieldInputState.Invalid(R.string.password_too_short, MIN_PASSWORD_LENGTH)
            else -> FieldInputState.Valid
        }
    }

    private fun getConfirmPasswordState(password: String?, confirmPassword:String?): FieldInputState {
        return when {
            confirmPassword == null -> FieldInputState.Invalid(R.string.empty_input)
            confirmPassword.isBlank() -> FieldInputState.Invalid(R.string.empty_input)
            confirmPassword != password -> FieldInputState.Invalid(R.string.passwords_do_not_match)
            else -> FieldInputState.Valid
        }
    }

    data class InputState (
        val email: FieldInputState,
        val password: FieldInputState,
        val confirmPassword: FieldInputState? = null
    )
    sealed class FieldInputState {
        object Valid: FieldInputState()
        data class Invalid(@StringRes val message: Int, val arg:Any? = null): FieldInputState()
    }
}