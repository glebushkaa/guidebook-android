package com.gm.ai.guidebook.ui.screen.details

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class DetailsNavigationEffect {

    data object NavigateBack : DetailsNavigationEffect()

    inline fun handle(
        navigateBack: () -> Unit = {},
    ) {
        when (this) {
            NavigateBack -> navigateBack()
        }
    }
}
