package com.gm.ai.guidebook.domain.repository

import com.gm.ai.guidebook.model.Guide

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

interface GuideRepository {

    suspend fun searchGuides(query: String): List<Guide>
}
