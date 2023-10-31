package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.core.common.FIVE_HUNDRED_MILLIS
import com.gm.ai.guidebook.ui.navigation.route.StepsScreenRoute
import com.gm.ai.guidebook.ui.screen.steps.StepsScreen
import com.gm.ai.guidebook.ui.screen.steps.StepsViewModel

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/31/2023
 */

private val animSpec = tween<IntOffset>(FIVE_HUNDRED_MILLIS.toInt())

fun NavGraphBuilder.stepsScreenDestination() {
    composable(
        route = StepsScreenRoute.routeWithArgs,
        arguments = StepsScreenRoute.arguments,
        enterTransition = {
            slideInVertically(
                animationSpec = animSpec,
                initialOffsetY = { it * Y_OFFSET_MULTIPLIER }
            )
        },
        exitTransition = {
            slideOutVertically(
                animationSpec = animSpec,
                targetOffsetY = { it * Y_OFFSET_MULTIPLIER }
            )
        },
        popExitTransition = {
            slideOutVertically(
                animationSpec = animSpec,
                targetOffsetY = { it * Y_OFFSET_MULTIPLIER }
            )
        },
    ) {
        val viewModel = hiltViewModel<StepsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        StepsScreen(
            state = state,
        )
    }
}

private const val Y_OFFSET_MULTIPLIER = 2