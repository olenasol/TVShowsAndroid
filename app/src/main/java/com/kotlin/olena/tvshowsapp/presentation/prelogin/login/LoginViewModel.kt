package com.kotlin.olena.tvshowsapp.presentation.prelogin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.olena.tvshowsapp.domain.models.FieldInputState
import com.kotlin.olena.tvshowsapp.domain.models.FirebaseAuthState
import com.kotlin.olena.tvshowsapp.domain.models.InputField
import com.kotlin.olena.tvshowsapp.domain.usecase.LoginUserUseCase
import com.kotlin.olena.tvshowsapp.other.InputValidationHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
): ViewModel() {
    private val _inputFieldsState = MutableStateFlow(mapOf<InputField, FieldInputState>(
        InputField.EMAIL to FieldInputState.Valid,
        InputField.PASSWORD to FieldInputState.Valid
    ))
    val inputFieldsState = _inputFieldsState.asStateFlow()

    private val _loginState = MutableSharedFlow<FirebaseAuthState>()
    val loginState: Flow<FirebaseAuthState> = _loginState

    fun loginUser(email:String?, password:String?){
        viewModelScope.launch {
            val inputStateMap = mapOf(
                InputField.EMAIL to InputValidationHelper.getEmailState(email),
                InputField.PASSWORD to InputValidationHelper.getPasswordState(password)
            )
            _inputFieldsState.emit(inputStateMap)
            if (InputValidationHelper.areAllFieldsValid(inputStateMap)) {
                if (email != null && password != null) {
                    _loginState.emitAll(loginUserUseCase.invoke(email, password))
                }
            }
        }
    }
}