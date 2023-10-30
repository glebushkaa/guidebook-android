package com.gm.ai.guidebook.ui.screen.settings

import com.gm.ai.guidebook.model.User

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

sealed class SettingsEvent {

    data object GetUser : SettingsEvent()
    data class UpdateUser(val user: User) : SettingsEvent()
    data object DeleteAccountClicked : SettingsEvent()
    data object LogOutClicked : SettingsEvent()

    data class SendNotificationsSettingUpdate(
        val notificationsEnabled: Boolean,
    ) : SettingsEvent()

    data class SendSystemDarkModeSetting(val darkMode: Boolean) : SettingsEvent()

    data object AlterDarkMode : SettingsEvent()
    data class UpdateDarkModeSetting(val darkMode: Boolean) : SettingsEvent()

    inline fun handle(
        alterDarkMode: () -> Unit = {},
        updateDarkModeSelector: (Boolean) -> Unit = {},
        sendSystemDarkModeSetting: (Boolean) -> Unit = {},
        sendNotificationsSettingUpdate: (Boolean) -> Unit = {},
        logOutClicked: () -> Unit = {},
        deleteAccountClicked: () -> Unit = {},
        updateUser: (User) -> Unit = {},
        getUser: () -> Unit = {},
    ) {
        when (this) {
            AlterDarkMode -> alterDarkMode()
            is UpdateDarkModeSetting -> updateDarkModeSelector(darkMode)
            is SendSystemDarkModeSetting -> sendSystemDarkModeSetting(darkMode)
            is SendNotificationsSettingUpdate -> sendNotificationsSettingUpdate(notificationsEnabled)
            LogOutClicked -> logOutClicked()
            DeleteAccountClicked -> deleteAccountClicked()
            is UpdateUser -> updateUser(user)
            GetUser -> getUser()
        }
    }
}
