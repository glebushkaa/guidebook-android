package com.gm.ai.guidebook.domain.usecase.core

import com.gm.ai.guidebook.domain.SessionBus
import retrofit2.HttpException
import java.net.HttpURLConnection

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 *
 * TODO REMOVE ANDROID DEPENDENCIES
 *
 */

abstract class ResultNoneParamsUseCase<Type : Any>(
    private val useCaseLogger: UseCaseLogger,
) : NoneParamsUseCase<Type> {

    abstract operator fun invoke(): Result<Type>

    @Suppress("TooGenericExceptionCaught")
    fun <T, P> T.runCatching(block: T.() -> P): Result<P> {
        return try {
            Result.success(block())
        } catch (throwable: Throwable) {
            if (throwable is HttpException && throwable.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                SessionBus.endSession()
            }
            useCaseLogger.logException(javaClass.simpleName, throwable)
            Result.failure(throwable)
        }
    }
}
