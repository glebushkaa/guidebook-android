package com.gm.ai.guidebook.domain.usecase.guides

import com.gm.ai.guidebook.domain.repository.GuideRepository
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import com.gm.ai.guidebook.domain.usecase.guides.GetGuidesByQueryUseCase.Params
import com.gm.ai.guidebook.model.Guide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

class GetGuidesByQueryUseCase @Inject constructor(
    private val guideRepository: GuideRepository,
    useCaseLogger: UseCaseLogger,
) : ResultSuspendUseCase<List<Guide>, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            guideRepository.searchGuides(params.query)
        }
    }

    data class Params(
        val query: String,
    ) : UseCase.Params
}
