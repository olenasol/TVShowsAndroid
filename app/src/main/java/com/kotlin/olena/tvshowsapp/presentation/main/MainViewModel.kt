package com.kotlin.olena.tvshowsapp.presentation.main

import androidx.lifecycle.ViewModel
import com.kotlin.olena.tvshowsapp.domain.usecase.AuthStateUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authStateUseCase: AuthStateUseCase
): ViewModel() {

    val authStateFlow = authStateUseCase.getAuthStateFlow()
}