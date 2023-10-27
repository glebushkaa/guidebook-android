package com.gm.ai.guidebook.data.repository

import com.gm.ai.guidebook.data.mapper.toGuide
import com.gm.ai.guidebook.data.network.GuidesApi
import com.gm.ai.guidebook.domain.repository.GuideRepository
import com.gm.ai.guidebook.model.Guide
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

@Singleton
class GuideRepositoryImpl @Inject constructor(
    private val guidesApi: GuidesApi,
) : GuideRepository {

    override suspend fun searchGuides(query: String): List<Guide> {
        return guidesApi.searchGuides(
            query = query,
        ).map { it.toGuide() }
    }
}
