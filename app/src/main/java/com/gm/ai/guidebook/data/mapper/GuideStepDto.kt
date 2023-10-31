package com.gm.ai.guidebook.data.mapper

import com.gm.ai.guidebook.data.network.dto.guide.GuideStepDto
import com.gm.ai.guidebook.model.Step

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/31/2023
 */

fun GuideStepDto.toStep(): Step {
    return Step(
        title = title ?: "",
        description = description ?: "",
        imageUrl = imageUrl ?: "",
        id = id ?: "",
        order = order ?: 0,
        guideId = guideId ?: "",
    )
}