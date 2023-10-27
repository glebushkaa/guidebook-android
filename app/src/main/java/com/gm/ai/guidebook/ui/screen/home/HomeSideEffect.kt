package com.gm.ai.guidebook.ui.screen.home

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

sealed class HomeSideEffect {

    data object ScrollTop : HomeSideEffect()

    inline fun handle(
        scrollTop: () -> Unit = {},
    ) = when (this) {
        ScrollTop -> scrollTop()
    }
}
