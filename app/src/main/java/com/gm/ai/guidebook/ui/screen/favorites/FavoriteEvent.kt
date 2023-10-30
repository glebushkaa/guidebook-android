package com.gm.ai.guidebook.ui.screen.favorites

import com.gm.ai.guidebook.model.Guide

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class FavoritesEvent {

    data object AskFavorites : FavoritesEvent()

    data class UpdateGuidesList(
        val list: List<Guide>,
    ) : FavoritesEvent()

    data class NavigateToDetailsScreen(
        val guideId: String,
    ) : FavoritesEvent()

    data class SendSearchQuery(
        val query: String,
    ) : FavoritesEvent()

    inline fun handle(
        updateGuidesList: (List<Guide>) -> Unit = {},
        navigateToDetailsScreen: (String) -> Unit = {},
        sendSearchQuery: (String) -> Unit = {},
        askFavorites: () -> Unit = {},
    ) {
        when (this) {
            is UpdateGuidesList -> updateGuidesList(list)
            is NavigateToDetailsScreen -> navigateToDetailsScreen(guideId)
            is SendSearchQuery -> sendSearchQuery(query)
            AskFavorites -> askFavorites()
        }
    }
}
