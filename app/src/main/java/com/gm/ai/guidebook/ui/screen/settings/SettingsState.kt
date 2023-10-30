package com.gm.ai.guidebook.ui.screen.settings

import com.gm.ai.guidebook.model.User

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

data class SettingsState(
    val darkModeEnabled: Boolean = false,
    val notificationsChecked: Boolean = false,
    val user: User,
)
