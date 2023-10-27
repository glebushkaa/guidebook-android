package com.gm.ai.guidebook.data.repository

import com.gm.ai.guidebook.data.mapper.toUser
import com.gm.ai.guidebook.data.network.AuthApi
import com.gm.ai.guidebook.data.network.dto.auth.SignInDto
import com.gm.ai.guidebook.data.network.dto.auth.SignOutDto
import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.exception.AuthException
import com.gm.ai.guidebook.domain.repository.AuthRepository
import com.gm.ai.guidebook.model.User
import kotlinx.coroutines.flow.firstOrNull
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
        val accessToken = authDataStore.accessToken.firstOrNull() ?: run {
            val exception = AuthException(
                code = NO_TOKEN_EXCEPTION,
                message = "No token found",
            )
            throw exception
        }
        val response = authApi.getUser(accessToken)
        if (response.message?.isNotEmpty() == true) {
            val exception = AuthException(
                code = REGISTER_EXCEPTION,
                message = response.message,
            )
            throw exception
        }
        return response.toUser()
    }

    private companion object {
        const val LOGIN_EXCEPTION = 100
        const val REGISTER_EXCEPTION = 200
        const val NO_TOKEN_EXCEPTION = -199
    }
}
