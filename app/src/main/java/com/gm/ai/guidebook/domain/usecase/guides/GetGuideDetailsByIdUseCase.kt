package com.gm.ai.guidebook.domain.usecase.guides

import com.gm.ai.guidebook.domain.repository.GuideRepository
import com.gm.ai.guidebook.domain.session.SessionStatusHandler
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import com.gm.ai.guidebook.domain.usecase.guides.GetGuideDetailsByIdUseCase.Params
import com.gm.ai.guidebook.model.GuideDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

class GetGuideDetailsByIdUseCase @Inject constructor(
    private val guideRepository: GuideRepository,
    useCaseLogger: UseCaseLogger,
    sessionStatusHandler: SessionStatusHandler
) : ResultSuspendUseCase<GuideDetails, Params>(
    useCaseLogger = useCaseLogger,
    sessionStatusHandler = sessionStatusHandler
) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            guideRepository.getGuideDetails(params.id)
        }
    }

    data class Params(
        val id: String,
    ) : UseCase.Params
}
