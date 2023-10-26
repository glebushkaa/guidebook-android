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
}
