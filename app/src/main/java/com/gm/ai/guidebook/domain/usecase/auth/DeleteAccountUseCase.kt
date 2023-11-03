package com.gm.ai.guidebook.domain.usecase.auth

import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.repository.AuthRepository
import com.gm.ai.guidebook.session.api.SessionStatusHandler
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendNoneParamsUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

class DeleteAccountUseCase @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val authRepository: AuthRepository,
    useCaseLogger: UseCaseLogger,
    sessionStatusHandler: SessionStatusHandler
) : ResultSuspendNoneParamsUseCase<Unit>(
    sessionStatusHandler = sessionStatusHandler,
    useCaseLogger = useCaseLogger
) {

    override suspend fun invoke() = runCatching {
        withContext(Dispatchers.IO) {
            authRepository.deleteAccount()
            authDataStore.removeAccessToken()
        }
    }
}
