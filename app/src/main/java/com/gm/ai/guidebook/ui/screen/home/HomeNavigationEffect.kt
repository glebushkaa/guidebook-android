package com.gm.ai.guidebook.ui.screen.home

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class HomeNavigationEffect {

    data class NavigateDetailsScreen(
        val guideId: String,
    ) : HomeNavigationEffect()

    inline fun handle(
        navigateDetailsScreen: (String) -> Unit = {},
    ) {
        when (this) {
            is NavigateDetailsScreen -> navigateDetailsScreen(guideId)
        }
    }
}
