package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.ui.navigation.route.HomeScreenRoute
import com.gm.ai.guidebook.ui.screen.home.HomeScreen
import com.gm.ai.guidebook.ui.screen.home.HomeViewModel

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

fun NavGraphBuilder.homeScreenDestination() {
    composable(route = HomeScreenRoute.route) {
        val viewModel = hiltViewModel<HomeViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        HomeScreen(
            state = state,
            onEvent = viewModel.state::handleEvent,
        )
    }
}
