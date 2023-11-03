package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.ui.navigation.route.SettingsScreenRoute
import com.gm.ai.guidebook.ui.screen.settings.SettingsScreen
import com.gm.ai.guidebook.ui.screen.settings.SettingsViewModel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

fun NavGraphBuilder.settingsScreenDestination(
    navigateLogin: () -> Unit,
) {
    composable(route = SettingsScreenRoute.route) {
        val viewModel = hiltViewModel<SettingsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navigationEffect by viewModel.navigationEffect
            .receiveAsFlow()
            .collectAsStateWithLifecycle(initialValue = null)

        SettingsScreen(
            state = state,
            sendEvent = viewModel.state::handleEvent,
        )

        LaunchedEffect(key1 = navigationEffect) {
            navigationEffect?.handle(navigateLogin = navigateLogin)
        }
    }
}
