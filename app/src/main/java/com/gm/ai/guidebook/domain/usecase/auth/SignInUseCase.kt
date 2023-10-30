package com.gm.ai.guidebook.domain.usecase.auth

import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.repository.AuthRepository
import com.gm.ai.guidebook.domain.usecase.auth.SignInUseCase.Params
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

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authDataStore: AuthDataStore,
    private val useCaseLogger: UseCaseLogger,
) : ResultSuspendUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val token = authRepository.login(params.email, params.password)
            authDataStore.updateAccessToken(token)
        } catch (httpException: HttpException) {
            useCaseLogger.logException(javaClass.simpleName, httpException)
            val response = httpException.response() ?: run {
                return@withContext Result.failure(httpException)
            }
            val json = response.errorBody()?.string()
            val signInResponse = Gson().fromJson(json, SignInResponse::class.java)
            val signInException = getSignInException(signInResponse.code, signInResponse.message)
            return@withContext Result.failure(signInException)
        } catch (exception: Exception) {
            useCaseLogger.logException(javaClass.simpleName, exception)
            return@withContext Result.failure(exception)
        }
        return@withContext Result.success(Unit)
    }

    private fun getSignInException(code: String, message: String?) = when (code) {
        PASSWORD_IS_NOT_VALID -> SignInException(
            field = LoginField.PASSWORD,
            message = message ?: "Invalid password",
        )

        USER_NOT_FOUND -> SignInException(
            field = LoginField.EMAIL,
            message = message ?: "User not found",
        )

        else -> SignInException(message = message ?: "Unknown error")
    }

    data class SignInResponse(
        @SerializedName("code") val code: String,
        @SerializedName("message") val message: String,
    )

    data class SignInException(
        val field: LoginField? = null,
        override val message: String,
    ) : Throwable()

    data class Params(val email: String, val password: String) : UseCase.Params

    private companion object {
        private const val PASSWORD_IS_NOT_VALID = "invalid-password"
        private const val USER_NOT_FOUND = "user-not-found"
    }
}
