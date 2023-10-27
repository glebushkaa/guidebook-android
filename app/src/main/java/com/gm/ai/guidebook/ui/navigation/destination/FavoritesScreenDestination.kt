package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.ui.navigation.route.FavoritesScreenRoute
import com.gm.ai.guidebook.ui.screen.favorites.FavoritesScreen
import com.gm.ai.guidebook.ui.screen.favorites.FavoritesViewModel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

fun NavGraphBuilder.favoritesScreenDestination(
    navigateDetailsScreen: (String) -> Unit = {},
) {
    composable(route = FavoritesScreenRoute.route) {
        val viewModel = hiltViewModel<FavoritesViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navEffect by viewModel.navigationEffect
            .receiveAsFlow()
            .collectAsStateWithLifecycle(initialValue = null)

        FavoritesScreen(
            state = state,
            onEvent = viewModel.state::handleEvent,
        )

        LaunchedEffect(key1 = navEffect) {
            navEffect?.handle(navigateDetailsScreen = navigateDetailsScreen)
        }
    }
}
