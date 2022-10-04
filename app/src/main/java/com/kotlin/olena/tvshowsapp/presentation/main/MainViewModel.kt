package com.kotlin.olena.tvshowsapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.olena.tvshowsapp.domain.usecase.AuthStateUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authStateUseCase: AuthStateUseCase
): ViewModel() {

    val authStateFlow = authStateUseCase.getAuthStateFlow().shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        1
    )
}