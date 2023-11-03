package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.ui.navigation.route.LoginScreenRoute
import com.gm.ai.guidebook.ui.screen.login.LoginScreen
import com.gm.ai.guidebook.ui.screen.login.LoginViewModel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/27/2023
 */

fun NavGraphBuilder.loginScreenDestination(
    navigateHome: () -> Unit = {},
) {
    composable(route = LoginScreenRoute.route) {
        val viewModel = hiltViewModel<LoginViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navigationEffect by viewModel.navigationEffect
            .receiveAsFlow()
            .collectAsStateWithLifecycle(null)

        LoginScreen(
            state = state,
            sendEvent = viewModel.state::handleEvent,
        )

        LaunchedEffect(key1 = navigationEffect) {
            navigationEffect?.handle(navigateHome = navigateHome)
        }
    }
}
