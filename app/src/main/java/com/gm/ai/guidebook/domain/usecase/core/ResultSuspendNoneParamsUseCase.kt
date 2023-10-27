package com.gm.ai.guidebook.domain.usecase.core

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

abstract class ResultSuspendNoneParamsUseCase<Type : Any>(
    private val useCaseLogger: UseCaseLogger,
) : NoneParamsUseCase<Type> {

    abstract suspend operator fun invoke(): Result<Type>

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T, P> T.runCatching(block: suspend T.() -> P): Result<P> {
        return try {
            Result.success(block())
        } catch (throwable: Throwable) {
            useCaseLogger.logException(javaClass.simpleName, throwable)
            Result.failure(throwable)
        }
    }
}
