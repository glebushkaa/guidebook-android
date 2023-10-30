package com.gm.ai.guidebook.domain.usecase.favorite

import com.gm.ai.guidebook.domain.repository.FavoritesRepository
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import com.gm.ai.guidebook.domain.usecase.favorite.AddGuideToFavoriteUseCase.Params
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/30/2023
 */

class AddGuideToFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    useCaseLogger: UseCaseLogger,
) : ResultSuspendUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            favoritesRepository.addFavorite(params.id)
        }
    }

    data class Params(
        val id: String,
    ) : UseCase.Params
}
