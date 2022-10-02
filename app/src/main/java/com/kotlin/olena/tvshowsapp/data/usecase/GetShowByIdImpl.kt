package com.kotlin.olena.tvshowsapp.data.usecase

import com.kotlin.olena.tvshowsapp.domain.models.ShowInfo
import com.kotlin.olena.tvshowsapp.domain.repository.ShowRepository
import com.kotlin.olena.tvshowsapp.domain.usecase.GetShowById

import javax.inject.Inject

class GetShowByIdImpl @Inject constructor(
    private val repository: ShowRepository
): GetShowById {
    override suspend fun invoke(id: Int): ShowInfo {
        return repository.getShowById(id).let { show ->
            ShowInfo(show.id, show.name, show.imageUrl, show.rating, show.language, show.status)
        }
    }

}