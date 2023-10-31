package com.gm.ai.guidebook.domain.usecase.favorite

import com.gm.ai.guidebook.domain.repository.FavoritesRepository
import com.gm.ai.guidebook.domain.session.SessionStatusHandler
import com.gm.ai.guidebook.domain.usecase.favorite.GetFavoritesUseCase.Params
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import com.gm.ai.guidebook.model.Guide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/30/2023
 */

class GetFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoritesRepository,
    useCaseLogger: UseCaseLogger,
    sessionStatusHandler: SessionStatusHandler
) : ResultSuspendUseCase<List<Guide>, Params>(
    useCaseLogger = useCaseLogger,
    sessionStatusHandler = sessionStatusHandler
) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            favoriteRepository.searchFavorites(query = params.query)
        }
    }

    data class Params(val query: String) : UseCase.Params
}
