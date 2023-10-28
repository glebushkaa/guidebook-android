package com.gm.ai.guidebook.data.repository

import com.gm.ai.guidebook.data.mapper.toUser
import com.gm.ai.guidebook.data.network.AuthApi
import com.gm.ai.guidebook.data.network.dto.auth.SignInDto
import com.gm.ai.guidebook.data.network.dto.auth.SignOutDto
import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.exception.AuthException
import com.gm.ai.guidebook.domain.repository.AuthRepository
import com.gm.ai.guidebook.model.User
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

class AuthRepositoryImpl @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val authApi: AuthApi,
) : AuthRepository {

    override suspend fun login(email: String, password: String): String {
        val signInDto = SignInDto(
            email = email,
            password = password,
        )
        val response = authApi.login(signInDto)
        return response.accessToken ?: run {
            val exception = AuthException(
                code = LOGIN_EXCEPTION,
                message = response.message ?: "Unknown error",
            )
            throw exception
        }
    }

    override suspend fun register(username: String, email: String, password: String): String {
        val signOutDto = SignOutDto(
            username = username,
            email = email,
            password = password,
        )
        val response = authApi.register(signOutDto)
        return response.accessToken ?: run {
            val exception = AuthException(
                code = REGISTER_EXCEPTION,
                message = response.message ?: "Unknown error",
            )
            throw exception
        }
    }

    override suspend fun getUser(): User {
        val accessToken = getAccessToken()
        val response = authApi.getUser(accessToken)
        if (response.message?.isNotEmpty() == true) {
            val exception = AuthException(
                code = GET_USER_EXCEPTION,
                message = response.message,
            )
            throw exception
        }
        return response.toUser()
    }

    override suspend fun deleteAccount() {
        val accessToken = getAccessToken()
        val response = authApi.deleteAccount(accessToken)
        if (response.code?.isNotEmpty() == true && response.message?.isNotEmpty() == true) {
            val exception = AuthException(
                code = DELETE_ACCOUNT_EXCEPTION,
                message = response.message,
            )
            throw exception
        }
    }

    private suspend fun getAccessToken(): String {
        return authDataStore.getAccessToken() ?: run {
            val exception = AuthException(
                code = NO_TOKEN_EXCEPTION,
                message = "No token",
            )
            throw exception
        }
    }

    private companion object {
        const val LOGIN_EXCEPTION = 100
        const val REGISTER_EXCEPTION = 200
        const val GET_USER_EXCEPTION = 300
        const val DELETE_ACCOUNT_EXCEPTION = -180
        const val NO_TOKEN_EXCEPTION = -199
    }
}
