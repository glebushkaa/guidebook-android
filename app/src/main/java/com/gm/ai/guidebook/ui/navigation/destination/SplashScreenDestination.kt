package com.gm.ai.guidebook.ui.navigation.destination

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gm.ai.guidebook.core.common.FIVE_HUNDRED_MILLIS
import com.gm.ai.guidebook.ui.navigation.route.SplashScreenRoute
import com.gm.ai.guidebook.ui.screen.splash.SplashScreen
import com.gm.ai.guidebook.ui.screen.splash.SplashViewModel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

private val exitAnimSpec = tween<Float>(FIVE_HUNDRED_MILLIS.toInt())

fun NavGraphBuilder.splashScreenDestination(
    homeNavigate: () -> Unit = {},
    loginNavigate: () -> Unit = {},
) {
    composable(
        route = SplashScreenRoute.route,
        exitTransition = { fadeOut(animationSpec = exitAnimSpec) },
    ) {
        val viewModel = hiltViewModel<SplashViewModel>()
        val navEffect by viewModel.navigationEffect
            .receiveAsFlow()
            .collectAsStateWithLifecycle(initialValue = null)

        SplashScreen()

        LaunchedEffect(key1 = navEffect) {
            navEffect?.handle(
                navigateLogin = loginNavigate,
                navigateHome = homeNavigate,
            )
        }
    }
}
