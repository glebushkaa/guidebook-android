package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.core.common.ONE_SECOND
import com.gm.ai.guidebook.ui.navigation.route.SplashScreenRoute
import com.gm.ai.guidebook.ui.screen.splash.SplashScreen

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

private val exitAnimSpec = tween<IntOffset>(ONE_SECOND.toInt())

fun NavGraphBuilder.splashScreenDestination(
    homeNavigate: () -> Unit = {},
) {
    composable(
        route = SplashScreenRoute.route,
        exitTransition = {
            slideOutHorizontally(
                animationSpec = exitAnimSpec,
                targetOffsetX = { -it },
            )
        },
    ) {
        SplashScreen(homeNavigate = homeNavigate)
    }
}
