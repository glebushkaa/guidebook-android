package com.gm.ai.guidebook.ui.screen.info

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class DetailsEvent {

    data object BackEvent : DetailsEvent()

    inline fun handle(
        backEvent: () -> Unit = {},
    ) {
        when (this) {
            is BackEvent -> backEvent()
        }
    }
}
