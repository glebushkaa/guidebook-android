package com.gm.ai.guidebook.domain.repository

import com.gm.ai.guidebook.model.Guide
import com.gm.ai.guidebook.model.GuideDetails
import com.gm.ai.guidebook.model.Step

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

interface GuideRepository {

    suspend fun searchGuides(query: String): List<Guide>

    suspend fun getGuideDetails(id: String): GuideDetails

    suspend fun getGuideSteps(id: String): List<Step>
}
