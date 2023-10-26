package com.gm.ai.guidebook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gm.ai.guidebook.ui.navigation.destination.splashScreenDestination
import com.gm.ai.guidebook.ui.navigation.route.HomeScreenRoute
import com.gm.ai.guidebook.ui.navigation.route.SplashScreenRoute

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Composable
fun GuideNavHost(
    modifier: Modifier = Modifier,
    controller: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = controller,
        startDestination = SplashScreenRoute.route,
    ) {
        splashScreenDestination {
            controller.navigate(HomeScreenRoute.route)
        }
    }
}
