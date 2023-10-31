package com.gm.ai.guidebook.domain

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/31/2023
 *
 * Maybe refactor or use another approach later to handle it
 *
 */

object SessionBus {

    val sessionAlive = Channel<Boolean>()

    fun endSession() {
        sessionAlive.trySend(true)
    }
}