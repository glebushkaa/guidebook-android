package com.gm.ai.guidebook.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.core.android.extensions.clickableWithoutRipple
import com.gm.ai.guidebook.model.User
import com.gm.ai.guidebook.ui.dialogs.ConfirmationDialog
import com.gm.ai.guidebook.ui.screen.settings.sections.AppearanceSection
import com.gm.ai.guidebook.ui.screen.settings.sections.LogOutSection
import com.gm.ai.guidebook.ui.screen.settings.sections.NotificationSection
import com.gm.ai.guidebook.ui.screen.settings.sections.ProfileSection
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Composable
fun SettingsScreen(
    state: SettingsState,
    sendEvent: (SettingsEvent) -> Unit = {},
) {
    val isSystemInDarkMode = isSystemInDarkTheme()

    LaunchedEffect(key1 = Unit) {
        val event = SettingsEvent.SendSystemDarkModeSetting(isSystemInDarkMode)
        sendEvent(event)
    }

    if (state.visibleDialog == SettingDialogs.DELETE) {
        ConfirmationDialog(
            title = stringResource(R.string.delete_account),
            description = stringResource(R.string.delete_account_question),
            onConfirm = {
                listOf(
                    SettingsEvent.HideDialogs,
                    SettingsEvent.DeleteAccountConfirmed
                ).forEach(sendEvent)
            },
            onCancel = {
                val event = SettingsEvent.HideDialogs
                sendEvent(event)
            },
        )
    }

    if (state.visibleDialog == SettingDialogs.LOG_OUT) {
        ConfirmationDialog(
            title = stringResource(R.string.log_out),
            description = stringResource(R.string.log_out_question),
            onConfirm = {
                listOf(
                    SettingsEvent.HideDialogs,
                    SettingsEvent.LogOutConfirmed
                ).forEach(sendEvent)
            },
            onCancel = {
                val event = SettingsEvent.HideDialogs
                sendEvent(event)
            },
        )
    }

    SettingsScreenContent(
        state = state,
        sendEvent = sendEvent,
    )
}

@Composable
fun SettingsScreenContent(
    state: SettingsState,
    sendEvent: (SettingsEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(GuideTheme.palette.background)
            .fillMaxSize()
            .padding(horizontal = GuideTheme.offset.regular),
    ) {
        Text(
            text = stringResource(R.string.profile),
            modifier = Modifier.padding(
                start = GuideTheme.offset.regular,
                top = GuideTheme.offset.regular,
            ),
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.onBackground,
        )
        ProfileSection(
            modifier = Modifier.padding(
                top = GuideTheme.offset.regular,
            ),
            user = state.user,
        )
        Text(
            text = stringResource(R.string.preferences),
            modifier = Modifier.padding(
                start = GuideTheme.offset.regular,
                top = GuideTheme.offset.large,
            ),
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.onBackground,
        )
        AppearanceSection(
            modifier = Modifier.padding(
                top = GuideTheme.offset.regular,
            ),
            darkModeEnabled = state.darkModeEnabled,
            onClick = {
                val event = SettingsEvent.AlterDarkMode
                sendEvent(event)
            },
        )
        NotificationSection(
            modifier = Modifier.padding(
                top = GuideTheme.offset.small,
            ),
            checked = state.notificationsChecked,
            onCheckedChange = {
                val event = SettingsEvent.SendNotificationsSettingUpdate(it)
                sendEvent(event)
            },
        )
        Text(
            text = stringResource(R.string.account),
            modifier = Modifier.padding(
                start = GuideTheme.offset.regular,
                top = GuideTheme.offset.large,
            ),
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.onBackground,
        )
        LogOutSection(
            modifier = Modifier.padding(
                top = GuideTheme.offset.regular,
            ),
            onClick = {
                val event = SettingsEvent.ShowLogOutAccountDialog
                sendEvent(event)
            },
        )
        Text(
            modifier = Modifier
                .padding(
                    top = GuideTheme.offset.regular,
                    start = GuideTheme.offset.regular,
                )
                .clickableWithoutRipple {
                    val event = SettingsEvent.ShowDeleteAccountDialog
                    sendEvent(event)
                },
            text = stringResource(R.string.delete_account),
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.error,
        )
    }
}
