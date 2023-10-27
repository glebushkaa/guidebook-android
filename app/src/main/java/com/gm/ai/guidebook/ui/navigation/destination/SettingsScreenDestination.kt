package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.ui.navigation.route.SettingsScreenRoute
import com.gm.ai.guidebook.ui.screen.settings.SettingsScreen
import com.gm.ai.guidebook.ui.screen.settings.SettingsViewModel

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

fun NavGraphBuilder.settingsScreenDestination() {
    composable(route = SettingsScreenRoute.route) {
        val viewModel = hiltViewModel<SettingsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        SettingsScreen(
            state = state,
            sendEvent = viewModel.state::handleEvent,
        )
    }
}
