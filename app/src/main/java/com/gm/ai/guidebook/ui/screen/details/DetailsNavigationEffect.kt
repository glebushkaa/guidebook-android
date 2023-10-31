package com.gm.ai.guidebook.ui.screen.details

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class DetailsNavigationEffect {

    data object NavigateBack : DetailsNavigationEffect()
    data class NavigateSteps(val guideId: String) : DetailsNavigationEffect()

    inline fun handle(
        navigateBack: () -> Unit = {},
        navigateSteps: (String) -> Unit = {},
    ) {
        when (this) {
            NavigateBack -> navigateBack()
            is NavigateSteps -> navigateSteps(guideId)
        }
    }
}
