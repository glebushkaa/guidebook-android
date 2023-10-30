package com.gm.ai.guidebook.ui.screen.settings

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.core.android.extensions.clickableWithoutRipple
import com.gm.ai.guidebook.model.User
import com.gm.ai.guidebook.ui.dialogs.ConfirmationDialog
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 *
 * TODO Extract to separate files
 *
 */

@Preview
@Composable
fun SettingsScreenPreview() {
    val state = SettingsState(
        darkModeEnabled = true,
        notificationsChecked = true,
        user = User(
            id = "da",
            username = "Gleb",
            email = "gle.bushkaa",
        ),
    )
    GuideBookTheme(darkTheme = state.darkModeEnabled) {
        SettingsScreen(
            state = state,
        )
    }
}

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
            title = "Delete account",
            description = "Are you sure you want to delete your account?",
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
            title = "Log out",
            description = "Are you sure you want to log out?",
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
            text = "Profile",
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
            text = "Preferences",
            modifier = Modifier.padding(
                start = GuideTheme.offset.regular,
                top = GuideTheme.offset.regular,
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
            text = "Account",
            modifier = Modifier.padding(
                start = GuideTheme.offset.regular,
                top = GuideTheme.offset.gigantic,
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
            text = "Delete account",
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.error,
        )
    }
}

@Composable
private fun AppearanceSection(
    modifier: Modifier = Modifier,
    darkModeEnabled: Boolean,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                color = GuideTheme.palette.surface,
                shape = GuideTheme.shape.medium,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier = Modifier.padding(start = GuideTheme.offset.regular),
            text = "Appearance",
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.onSurface,
        )
        Button(
            modifier = Modifier
                .padding(end = GuideTheme.offset.regular)
                .size(40.dp)
                .align(Alignment.CenterEnd),
            contentPadding = PaddingValues(GuideTheme.offset.tiny),
            shape = GuideTheme.shape.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = GuideTheme.palette.onSurface.copy(alpha = 0.4f),
            ),
            onClick = onClick,
        ) {
            val icon = if (darkModeEnabled) R.drawable.ic_moon else R.drawable.ic_sun
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = GuideTheme.palette.surface,
            )
        }
    }
}

@Composable
private fun NotificationSection(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                color = GuideTheme.palette.surface,
                shape = GuideTheme.shape.medium,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier = Modifier.padding(start = GuideTheme.offset.regular),
            text = "Notifications",
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.onSurface,
        )
        Switch(
            modifier = Modifier
                .padding(end = GuideTheme.offset.regular)
                .align(Alignment.CenterEnd),
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = GuideTheme.palette.primary,
            )
        )
    }
}

@Composable
private fun LogOutSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                color = GuideTheme.palette.surface,
                shape = GuideTheme.shape.medium,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier = Modifier
                .padding(start = GuideTheme.offset.regular)
                .clickableWithoutRipple(onClick),
            text = "Log out",
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.error,
        )
    }
}

@Composable
private fun ProfileSection(
    modifier: Modifier = Modifier,
    user: User,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                color = GuideTheme.palette.surface,
                shape = GuideTheme.shape.medium,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(start = GuideTheme.offset.medium)
                .size(40.dp)
                .background(
                    color = GuideTheme.palette.onBackground,
                    shape = GuideTheme.shape.round,
                ),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            tint = GuideTheme.palette.background,
        )
        Column(
            modifier = Modifier.padding(
                horizontal = GuideTheme.offset.regular,
            ),
        ) {
            Text(
                text = user.username,
                style = GuideTheme.typography.titleMedium,
                color = GuideTheme.palette.onSurface,
            )
            Text(
                text = user.email,
                style = GuideTheme.typography.bodyMedium,
                color = GuideTheme.palette.onSurface.copy(
                    alpha = 0.6f,
                ),
            )
        }
    }
}
