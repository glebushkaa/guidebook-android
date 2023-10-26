package com.gm.ai.guidebook.ui.screen.home

import com.gm.ai.guidebook.model.Guide

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class HomeEvent {

    data class UpdateGuidesList(
        val list: List<Guide>,
    ) : HomeEvent()

    data class SendSearchQuery(
        val query: String,
    ) : HomeEvent()

    data class NavigateToDetailsScreen(
        val guideId: Long,
    ) : HomeEvent()

    inline fun handle(
        updateGuidesList: (List<Guide>) -> Unit = {},
        sendSearchQuery: (String) -> Unit = {},
        navigateToDetailsScreen: (Long) -> Unit = {},
    ) {
        when (this) {
            is UpdateGuidesList -> updateGuidesList(list)
            is SendSearchQuery -> sendSearchQuery(query)
            is NavigateToDetailsScreen -> navigateToDetailsScreen(guideId)
        }
    }
}
