package com.gm.ai.guidebook.domain.usecase.auth

import com.gm.ai.guidebook.domain.repository.AuthRepository
import com.gm.ai.guidebook.domain.session.SessionStatusHandler
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendNoneParamsUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import com.gm.ai.guidebook.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    useCaseLogger: UseCaseLogger,
    sessionStatusHandler: SessionStatusHandler
) : ResultSuspendNoneParamsUseCase<User>(
    sessionStatusHandler = sessionStatusHandler,
    useCaseLogger = useCaseLogger
) {

    override suspend fun invoke() = runCatching {
        withContext(Dispatchers.IO) { authRepository.getUser() }
    }
}
