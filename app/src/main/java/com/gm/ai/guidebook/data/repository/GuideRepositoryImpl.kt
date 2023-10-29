package com.gm.ai.guidebook.data.repository

import com.gm.ai.guidebook.data.mapper.toGuide
import com.gm.ai.guidebook.data.mapper.toGuideDetails
import com.gm.ai.guidebook.data.network.GuidesApi
import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.exception.AuthException
import com.gm.ai.guidebook.domain.repository.GuideRepository
import com.gm.ai.guidebook.model.Guide
import com.gm.ai.guidebook.model.GuideDetails
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

@Singleton
class GuideRepositoryImpl @Inject constructor(
    private val guidesApi: GuidesApi,
    private val authDataStore: AuthDataStore,
) : GuideRepository {

    override suspend fun searchGuides(query: String): List<Guide> {
        return guidesApi.searchGuides(
            token = getAccessToken(),
            query = query,
        ).map { it.toGuide() }
    }

    override suspend fun getGuideDetails(id: String): GuideDetails {
        val accessToken = getAccessToken()
        return guidesApi.getGuideDetails(
            token = accessToken,
            id = id,
        ).toGuideDetails()
    }

    private suspend fun getAccessToken(): String {
        return authDataStore.getAccessToken() ?: run {
            val exception = AuthException(
                code = NO_TOKEN_EXCEPTION,
                message = "No token found",
            )
            throw exception
        }
    }

    private companion object {
        const val NO_TOKEN_EXCEPTION = -199
    }
}
