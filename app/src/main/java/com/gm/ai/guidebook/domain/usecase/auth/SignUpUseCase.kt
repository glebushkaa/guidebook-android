package com.gm.ai.guidebook.domain.usecase.auth

import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.repository.AuthRepository
import com.gm.ai.guidebook.domain.usecase.auth.SignUpUseCase.Params
import com.gm.ai.guidebook.domain.usecase.core.ResultSuspendUseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCase
import com.gm.ai.guidebook.domain.usecase.core.UseCaseLogger
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 *
 * TODO Refactor it later
 * Remove android dependency from domain layer
 *
 */

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authDataStore: AuthDataStore,
    private val useCaseLogger: UseCaseLogger,
) : ResultSuspendUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val token = authRepository.register(
                username = params.username,
                email = params.email,
                password = params.password,
            )
            authDataStore.updateAccessToken(token)
        } catch (httpException: HttpException) {
            useCaseLogger.logException(javaClass.simpleName, httpException)
            val response = httpException.response() ?: run {
                return@withContext Result.failure(httpException)
            }
            val json = response.errorBody()?.string()
            val signOut = Gson().fromJson(json, SignOut::class.java)
            val signUpException = getSignUpException(
                code = signOut.code,
                message = signOut.message,
            )
            return@withContext Result.failure(signUpException)
        } catch (exception: Exception) {
            useCaseLogger.logException(javaClass.simpleName, exception)
            return@withContext Result.failure(exception)
        }
        return@withContext Result.success(Unit)
    }

    private fun getSignUpException(code: String, message: String?) = when (code) {
        EMAIL_NOT_UNIQUE -> SignUpException(
            field = LoginField.EMAIL,
            message = message ?: "Email is already taken",
        )

        USERNAME_NOT_UNIQUE -> SignUpException(
            field = LoginField.USERNAME,
            message = message ?: "Username is already taken",
        )

        else -> SignUpException(message = message ?: "Unknown error")
    }

    data class SignOut(
        @SerializedName("code") val code: String,
        @SerializedName("message") val message: String,
    )

    data class SignUpException(
        val field: LoginField? = null,
        override val message: String,
    ) : Throwable()

    data class Params(
        val username: String,
        val email: String,
        val password: String,
    ) : UseCase.Params

    private companion object {
        private const val EMAIL_NOT_UNIQUE = "email-not-unique"
        private const val USERNAME_NOT_UNIQUE = "username-not-unique"
    }
}
