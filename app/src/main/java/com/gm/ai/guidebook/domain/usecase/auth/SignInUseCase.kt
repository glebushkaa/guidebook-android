package com.gm.ai.guidebook.domain.usecase.auth

import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.repository.AuthRepository
import com.gm.ai.guidebook.session.api.SessionStatusHandler
import com.gm.ai.guidebook.domain.usecase.auth.SignInUseCase.Params
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authDataStore: AuthDataStore,
    useCaseLogger: UseCaseLogger,
    sessionStatusHandler: SessionStatusHandler
) : ResultSuspendUseCase<Unit, Params>(
    useCaseLogger = useCaseLogger,
    sessionStatusHandler = sessionStatusHandler
) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            val token = authRepository.login(params.email, params.password)
            authDataStore.updateAccessToken(token)
        }
    }

    data class SignInException(
        val field: LoginField? = null,
        override val message: String,
    ) : Throwable()

    data class Params(val email: String, val password: String) : UseCase.Params
}
