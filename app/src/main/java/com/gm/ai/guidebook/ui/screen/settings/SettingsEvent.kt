package com.gm.ai.guidebook.ui.screen.settings

import com.gm.ai.guidebook.model.User

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class SettingsEvent {
    data object ShowDeleteAccountDialog : SettingsEvent()
    data object ShowLogOutAccountDialog : SettingsEvent()
    data object HideDialogs : SettingsEvent()

    data class UpdateUser(val user: User) : SettingsEvent()
    data object DeleteAccountConfirmed : SettingsEvent()
    data object LogOutConfirmed : SettingsEvent()
    data class SendNotificationsSettingUpdate(val notificationsEnabled: Boolean) : SettingsEvent()
    data class SendSystemDarkModeSetting(val darkMode: Boolean) : SettingsEvent()
    data object AlterDarkMode : SettingsEvent()
    data class UpdateDarkModeSetting(val darkMode: Boolean) : SettingsEvent()
}
