package com.gm.ai.guidebook.ui.screen.favorites

import androidx.compose.runtime.Stable
import com.gm.ai.guidebook.model.Guide

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Stable
data class FavoritesState(
    val guides: List<Guide> = emptyList(),
    val searchQuery: String = "",
)
