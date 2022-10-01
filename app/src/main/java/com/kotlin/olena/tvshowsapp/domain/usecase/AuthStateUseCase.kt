package com.kotlin.olena.tvshowsapp.domain.usecase

import kotlinx.coroutines.flow.Flow

interface AuthStateUseCase {
    fun getAuthStateFlow(): Flow<Boolean>
}