package com.gm.ai.guidebook.domain.usecase.auth

import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.repository.AuthRepository
import com.gm.ai.guidebook.domain.usecase.auth.SignUpUseCase.Params
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authDataStore: AuthDataStore,
    useCaseLogger: UseCaseLogger,
) : ResultSuspendUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            val token = authRepository.register(
                username = params.username,
                email = params.email,
                password = params.password,
            )
            authDataStore.updateAccessToken(token)
        }
    }

    data class Params(
        val username: String,
        val email: String,
        val password: String,
    ) : UseCase.Params
}
