package com.gm.ai.guidebook.ui.screen.favorites

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class FavoritesNavigationEffect {

    data class NavigateDetailsScreen(
        val guideId: String,
    ) : FavoritesNavigationEffect()

    inline fun handle(
        navigateDetailsScreen: (String) -> Unit = {},
    ) {
        when (this) {
            is NavigateDetailsScreen -> navigateDetailsScreen(guideId)
        }
    }
}
