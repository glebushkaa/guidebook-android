package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.ui.navigation.route.HomeScreenRoute
import com.gm.ai.guidebook.ui.screen.home.HomeScreen
import com.gm.ai.guidebook.ui.screen.home.HomeViewModel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

fun NavGraphBuilder.homeScreenDestination(
    navigateDetailsScreen: (Long) -> Unit = {},
) {
    composable(route = HomeScreenRoute.route) {
        val viewModel = hiltViewModel<HomeViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navEffect by viewModel.navigationEffect
            .receiveAsFlow()
            .collectAsStateWithLifecycle(initialValue = null)

        HomeScreen(
            state = state,
            onEvent = viewModel.state::handleEvent,
        )

        LaunchedEffect(key1 = navEffect) {
            navEffect?.handle(navigateDetailsScreen = navigateDetailsScreen)
        }
    }
}
