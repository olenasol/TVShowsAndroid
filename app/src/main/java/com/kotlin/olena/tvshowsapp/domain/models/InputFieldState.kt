package com.kotlin.olena.tvshowsapp.domain.models

import androidx.annotation.StringRes


enum class InputField {
    EMAIL, PASSWORD, CONFIRM_PASSWORD
}

sealed class FieldInputState {
    object Valid: FieldInputState()
    data class Invalid(@StringRes val message: Int, val arg:Any? = null): FieldInputState()
}