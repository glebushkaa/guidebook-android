package com.gm.ai.guidebook.ui.screen.home

import com.gm.ai.guidebook.model.Guide

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

data class HomeState(
    val id: Int = 0,
    val searchQuery: String = "",
    val guides: List<Guide> = emptyList(),
)
