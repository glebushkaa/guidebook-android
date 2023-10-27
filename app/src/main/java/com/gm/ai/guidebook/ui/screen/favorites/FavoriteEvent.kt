package com.gm.ai.guidebook.ui.screen.favorites

import com.gm.ai.guidebook.model.Guide
import com.gm.ai.guidebook.ui.screen.home.HomeEvent

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class FavoritesEvent {

    data class UpdateGuidesList(
        val list: List<Guide>,
    ) : FavoritesEvent()

    data class SendSearchQuery(
        val query: String,
    ) : FavoritesEvent()

    data class NavigateToDetailsScreen(
        val guideId: String,
    ) : FavoritesEvent()

    inline fun handle(
        updateGuidesList: (List<Guide>) -> Unit = {},
        sendSearchQuery: (String) -> Unit = {},
        navigateToDetailsScreen: (String) -> Unit = {},
    ) {
        when (this) {
            is UpdateGuidesList -> updateGuidesList(list)
            is SendSearchQuery -> sendSearchQuery(query)
            is NavigateToDetailsScreen -> navigateToDetailsScreen(guideId)
        }
    }
}
