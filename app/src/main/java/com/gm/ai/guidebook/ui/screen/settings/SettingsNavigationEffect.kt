package com.gm.ai.guidebook.ui.screen.settings

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/28/2023
 */

sealed class SettingsNavigationEffect {
    data object NavigateLogin : SettingsNavigationEffect()

    inline fun handle(
        navigateLogin: () -> Unit,
    ) {
        when (this) {
            is NavigateLogin -> navigateLogin()
        }
    }
}
