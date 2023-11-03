package com.gm.ai.guidebook.ui.screen.details

import com.gm.ai.guidebook.model.GuideDetails

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class DetailsEvent {

    data object BackEvent : DetailsEvent()
    data class UpdateGuideDetails(
        val guideDetails: GuideDetails,
        val stepsVisible: Boolean = false,
    ) : DetailsEvent()

    data object LikeClicked : DetailsEvent()
    data object OpenSteps : DetailsEvent()
}
