package com.gm.ai.guidebook.data.mapper

import com.gm.ai.guidebook.data.network.dto.GuideDto
import com.gm.ai.guidebook.model.Guide

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

fun GuideDto.toGuide(): Guide {
    return Guide(
        id = id ?: "",
        title = title ?: "",
        emoji = emoji ?: "",
        description = description ?: "",
        imageUrl = imageUrl ?: "",
        authorId = authorId ?: "",
    )
}
