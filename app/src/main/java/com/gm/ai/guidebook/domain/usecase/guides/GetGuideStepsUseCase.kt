package com.gm.ai.guidebook.domain.usecase.guides

import com.gm.ai.guidebook.domain.repository.GuideRepository
import com.gm.ai.guidebook.session.api.SessionStatusHandler
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import com.gm.ai.guidebook.domain.usecase.guides.GetGuideStepsUseCase.Params
import com.gm.ai.guidebook.model.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/31/2023
 */

class GetGuideStepsUseCase @Inject constructor(
    private val guideRepository: GuideRepository,
    useCaseLogger: UseCaseLogger,
    sessionStatusHandler: SessionStatusHandler
) : ResultSuspendUseCase<List<Step>, Params>(
    useCaseLogger = useCaseLogger,
    sessionStatusHandler = sessionStatusHandler
) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            guideRepository.getGuideSteps(params.guideId)
        }
    }

    data class Params(
        val guideId: String
    ) : UseCase.Params
}