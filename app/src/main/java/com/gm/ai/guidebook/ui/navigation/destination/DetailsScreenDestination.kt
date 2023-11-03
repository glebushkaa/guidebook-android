package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.core.common.FIVE_HUNDRED_MILLIS
import com.gm.ai.guidebook.ui.navigation.route.DetailsScreenRoute
import com.gm.ai.guidebook.ui.navigation.route.StepsScreenRoute
import com.gm.ai.guidebook.ui.screen.details.DetailsScreen
import com.gm.ai.guidebook.ui.screen.details.DetailsViewModel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

private val animSpec = tween<IntOffset>(FIVE_HUNDRED_MILLIS.toInt())

fun NavGraphBuilder.detailsScreenDestination(
    navigateBack: () -> Unit = {},
    navigateSteps: (String) -> Unit = {},
) {
    composable(
        route = DetailsScreenRoute.routeWithArgs,
        arguments = DetailsScreenRoute.arguments,
        enterTransition = { makeEnterTransition() },
        popEnterTransition = { makeEnterTransition() },
        exitTransition = { makeExitTransition() },
        popExitTransition = { makeExitTransition() },
    ) {
        val viewModel = hiltViewModel<DetailsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val navEffect by viewModel.navigationEffect
            .receiveAsFlow()
            .collectAsStateWithLifecycle(initialValue = null)

        DetailsScreen(
            state = state,
            onEvent = viewModel.state::handleEvent,
        )

        LaunchedEffect(key1 = navEffect) {
            navEffect?.handle(
                navigateBack = navigateBack,
                navigateSteps = navigateSteps,
            )
        }
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.makeEnterTransition(): EnterTransition? {
    return if (initialState.destination.route == StepsScreenRoute.routeWithArgs) {
        null
    } else {
        slideInHorizontally(
            animationSpec = animSpec,
            initialOffsetX = { it * X_OFFSET_MULTIPLIER }
        )
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.makeExitTransition(): ExitTransition? {
    return if (targetState.destination.route == StepsScreenRoute.routeWithArgs) {
        null
    } else {
        slideOutHorizontally(
            animationSpec = animSpec,
            targetOffsetX = { it * X_OFFSET_MULTIPLIER }
        )
    }
}

private const val X_OFFSET_MULTIPLIER = 2