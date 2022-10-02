package com.kotlin.olena.tvshowsapp.data.usecase

import com.kotlin.olena.tvshowsapp.domain.models.ShowGeneralInfo
import com.kotlin.olena.tvshowsapp.domain.repository.ShowRepository
import com.kotlin.olena.tvshowsapp.domain.usecase.GetShowListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShowListUseCaseImpl @Inject constructor(
    private val repo: ShowRepository
): GetShowListUseCase {
    override suspend fun invoke(): Flow<List<ShowGeneralInfo>> {
        return repo.retrieveListOfShows().map { shows ->
            shows.map { show ->
                ShowGeneralInfo(show.id, show.name, show.imageUrl.orEmpty(), false)
            }
        }
    }
}