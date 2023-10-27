package com.gm.ai.guidebook.domain.repository

import com.gm.ai.guidebook.model.User

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

interface AuthRepository {

    /**
     * @return access token
     */
    suspend fun login(
        email: String,
        password: String,
    ): String

    /**
     * @return access token
     */
    suspend fun register(
        username: String,
        email: String,
        password: String,
    ): String

    suspend fun getUser(): User
}
