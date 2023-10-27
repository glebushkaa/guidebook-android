package com.gm.ai.guidebook.domain.usecase.auth

import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendNoneParamsUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

class LogOutUseCase @Inject constructor(
    private val authDataStore: AuthDataStore,
    useCaseLogger: UseCaseLogger,
) : ResultSuspendNoneParamsUseCase<Unit>(useCaseLogger) {

    override suspend fun invoke() = runCatching {
        withContext(Dispatchers.IO) {
            authDataStore.removeAccessToken()
        }
    }
}
