package com.kotlin.olena.tvshowsapp.domain.usecase

import com.kotlin.olena.tvshowsapp.domain.models.ShowGeneralInfo
import kotlinx.coroutines.flow.Flow

interface GetShowListUseCase {
    suspend fun invoke(): Flow<List<ShowGeneralInfo>>
}