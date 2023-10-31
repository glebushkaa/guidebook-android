package com.gm.ai.guidebook.ui.screen.details

import androidx.compose.runtime.Stable
import com.gm.ai.guidebook.model.GuideDetails

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Stable
data class DetailsState(
    val guide: GuideDetails,
    val stepsButtonVisible: Boolean = false,
)
