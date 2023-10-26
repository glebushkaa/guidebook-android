package com.gm.ai.guidebook.model

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

data class Guide(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val favorite: Boolean = false,
)
