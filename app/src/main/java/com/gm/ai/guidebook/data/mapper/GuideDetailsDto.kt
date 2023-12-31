package com.gm.ai.guidebook.data.mapper

import com.gm.ai.guidebook.data.network.dto.guide.GuideDetailsDto
import com.gm.ai.guidebook.model.GuideDetails

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

fun GuideDetailsDto.toGuideDetails(): GuideDetails = GuideDetails(
    id = id ?: "",
    title = title ?: "",
    emoji = emoji ?: "",
    description = description ?: "",
    imageUrl = imageUrl ?: "",
    authorId = authorId ?: "",
    authorName = author?.name ?: "",
    favorite = favorite,
)
