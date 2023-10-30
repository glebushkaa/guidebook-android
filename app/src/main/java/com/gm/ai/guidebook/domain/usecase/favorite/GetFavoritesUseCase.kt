package com.gm.ai.guidebook.domain.usecase.favorite

import com.gm.ai.guidebook.domain.repository.FavoritesRepository
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendNoneParamsUseCase
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
) : ResultSuspendNoneParamsUseCase<List<Guide>>(useCaseLogger) {
    override suspend fun invoke() = runCatching {
        withContext(Dispatchers.IO) {
            favoriteRepository.getFavorites()
        }
    }
}
