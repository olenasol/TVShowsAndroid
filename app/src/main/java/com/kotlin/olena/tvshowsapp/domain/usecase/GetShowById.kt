package com.kotlin.olena.tvshowsapp.domain.usecase

import com.kotlin.olena.tvshowsapp.domain.models.ShowInfo
import kotlinx.coroutines.flow.Flow

interface GetShowById {
    suspend fun invoke(id: Int): ShowInfo
}