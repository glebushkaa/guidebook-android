package com.gm.ai.guidebook.data.repository

import com.gm.ai.guidebook.data.mapper.toGuide
import com.gm.ai.guidebook.data.network.GuidesApi
import com.gm.ai.guidebook.domain.datastore.AuthDataStore
import com.gm.ai.guidebook.domain.exception.AuthException
import com.gm.ai.guidebook.domain.repository.GuideRepository
import com.gm.ai.guidebook.model.Guide
import kotlinx.coroutines.flow.firstOrNull
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
        val accessToken = authDataStore.accessToken.firstOrNull() ?: run {
            val exception = AuthException(
                code = NO_TOKEN_EXCEPTION,
                message = "No token found",
            )
            throw exception
        }
        return guidesApi.searchGuides(
            token = accessToken,
            query = query,
        ).map { it.toGuide() }
    }

    private companion object {
        const val NO_TOKEN_EXCEPTION = -199
    }
}
