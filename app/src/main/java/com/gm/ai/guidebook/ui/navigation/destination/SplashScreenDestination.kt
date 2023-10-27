package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.core.common.FIVE_HUNDRED_MILLIS
import com.gm.ai.guidebook.ui.navigation.route.SplashScreenRoute
import com.gm.ai.guidebook.ui.screen.splash.SplashScreen

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

private val exitAnimSpec = tween<Float>(FIVE_HUNDRED_MILLIS.toInt())

fun NavGraphBuilder.splashScreenDestination(
    homeNavigate: () -> Unit = {},
) {
    composable(
        route = SplashScreenRoute.route,
        exitTransition = { fadeOut(animationSpec = exitAnimSpec) },
    ) {
        SplashScreen(homeNavigate = homeNavigate)
    }
}
