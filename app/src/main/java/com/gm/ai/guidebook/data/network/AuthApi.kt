package com.gm.ai.guidebook.data.network

import com.gm.ai.guidebook.data.network.dto.auth.AuthResponseDto
import com.gm.ai.guidebook.data.network.dto.auth.SignInDto
import com.gm.ai.guidebook.data.network.dto.auth.SignOutDto
import com.gm.ai.guidebook.data.network.dto.auth.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

interface AuthApi {

    @POST("auth/signin")
    suspend fun login(
        @Body signInDto: SignInDto,
    ): AuthResponseDto

    @POST("auth/signup")
    suspend fun register(
        @Body signOutDto: SignOutDto,
    ): AuthResponseDto

    @GET("auth/me")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserDto
}
