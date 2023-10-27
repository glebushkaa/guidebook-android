package com.gm.ai.guidebook.data.network

import com.gm.ai.guidebook.data.network.dto.GuideDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

interface GuidesApi {

    @GET("search/guides")
    suspend fun searchGuides(
        @Header("Authorization") token: String = "",
        @Query("query") query: String = "",
    ): List<GuideDto>
}
