package com.gm.ai.guidebook.ui.screen.details

import com.gm.ai.guidebook.model.GuideDetails

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class DetailsEvent {

    data object BackEvent : DetailsEvent()

    data class UpdateGuideDetails(val guideDetails: GuideDetails) : DetailsEvent()

    data object LikeClicked : DetailsEvent()

    inline fun handle(
        backEvent: () -> Unit = {},
        updateGuideDetails: (GuideDetails) -> Unit = {},
        likedClicked: () -> Unit = {},
    ) {
        when (this) {
            is BackEvent -> backEvent()
            is UpdateGuideDetails -> updateGuideDetails(guideDetails)
            LikeClicked -> likedClicked()
        }
    }
}
