package com.kotlin.olena.tvshowsapp.presentation.prelogin.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.olena.tvshowsapp.domain.models.FieldInputState
import com.kotlin.olena.tvshowsapp.domain.models.InputField
import com.kotlin.olena.tvshowsapp.domain.models.FirebaseAuthState
import com.kotlin.olena.tvshowsapp.domain.usecase.RegisterNewUserUseCase
import com.kotlin.olena.tvshowsapp.other.InputValidationHelper.getEmailState
import com.kotlin.olena.tvshowsapp.other.InputValidationHelper.getPasswordState
import com.kotlin.olena.tvshowsapp.other.InputValidationHelper.getConfirmPasswordState
import com.kotlin.olena.tvshowsapp.other.InputValidationHelper.areAllFieldsValid
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerNewUserUseCase: RegisterNewUserUseCase
) : ViewModel() {

    private val _inputFieldsState = MutableStateFlow(mapOf<InputField, FieldInputState>(
        InputField.EMAIL to FieldInputState.Valid,
        InputField.PASSWORD to FieldInputState.Valid,
        InputField.CONFIRM_PASSWORD to FieldInputState.Valid,
    ))
    val inputFieldsState = _inputFieldsState.asStateFlow()

    private val _registerState = MutableSharedFlow<FirebaseAuthState>()
    val registerState:Flow<FirebaseAuthState> = _registerState

    fun registerUser(email:String?, password:String?, confirmPassword:String?){
        viewModelScope.launch {
            val inputStateMap = mapOf(
                InputField.EMAIL to getEmailState(email),
                InputField.PASSWORD to getPasswordState(password),
                InputField.CONFIRM_PASSWORD to getConfirmPasswordState(password, confirmPassword)
            )
            _inputFieldsState.emit(inputStateMap)
            if (areAllFieldsValid(inputStateMap)) {
                if (email != null && password != null) {
                    _registerState.emitAll(registerNewUserUseCase.invoke(email, password))
                }
            }
        }
    }

}