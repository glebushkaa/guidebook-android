package com.gm.ai.guidebook.ui.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.ui.navigation.route.SplashScreenRoute
import com.gm.ai.guidebook.ui.screen.splash.SplashScreen

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

fun NavGraphBuilder.splashScreenDestination(
    homeNavigate: () -> Unit = {},
) {
    composable(route = SplashScreenRoute.route) {
        SplashScreen(homeNavigate = homeNavigate)
    }
}
