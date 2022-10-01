package com.kotlin.olena.tvshowsapp.other

import android.content.Context
import android.util.Patterns
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.domain.models.FieldInputState
import com.kotlin.olena.tvshowsapp.domain.models.InputField

object InputValidationHelper {

    fun areAllFieldsValid(inputStateMap: Map<InputField, FieldInputState>) =
        (inputStateMap.values.find { it is FieldInputState.Invalid } == null)

    fun getEmailState(email: String?): FieldInputState {
        return when {
            email == null -> FieldInputState.Invalid(R.string.empty_input)
            email.isBlank() -> FieldInputState.Invalid(R.string.empty_input)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> FieldInputState.Invalid(R.string.incorrect_email_format)
            else -> FieldInputState.Valid
        }
    }

    fun getPasswordState(password: String?): FieldInputState {
        return when {
            password == null -> FieldInputState.Invalid(R.string.empty_input)
            password.isBlank() -> FieldInputState.Invalid(R.string.empty_input)
            password.length < MIN_PASSWORD_LENGTH -> FieldInputState.Invalid(R.string.password_too_short, MIN_PASSWORD_LENGTH)
            else -> FieldInputState.Valid
        }
    }

    fun getConfirmPasswordState(password: String?, confirmPassword:String?): FieldInputState {
        return when {
            confirmPassword == null -> FieldInputState.Invalid(R.string.empty_input)
            confirmPassword.isBlank() -> FieldInputState.Invalid(R.string.empty_input)
            confirmPassword != password -> FieldInputState.Invalid(R.string.passwords_do_not_match)
            else -> FieldInputState.Valid
        }
    }
    fun getErrorInputMessage(context: Context?, inputStateMap: Map<InputField, FieldInputState>,
                             key: InputField
    ): String? {
        return when (val inputState = inputStateMap[key]) {
            is FieldInputState.Valid -> null
            is FieldInputState.Invalid -> context?.getString(
                inputState.message,
                inputState.arg
            )
            else -> null
        }
    }
}