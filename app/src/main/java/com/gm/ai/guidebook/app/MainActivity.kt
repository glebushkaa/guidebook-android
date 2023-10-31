package com.gm.ai.guidebook.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gm.ai.guidebook.core.android.extensions.navigatePopUpInclusive
import com.gm.ai.guidebook.session.api.SessionStatus
import com.gm.ai.guidebook.ui.components.AnimatedTopBar
import com.gm.ai.guidebook.ui.dialogs.SessionExpiredDialog
import com.gm.ai.guidebook.ui.navigation.GuideNavHost
import com.gm.ai.guidebook.ui.navigation.components.AnimateBottomNavBar
import com.gm.ai.guidebook.ui.navigation.route.LoginScreenRoute
import com.gm.ai.guidebook.ui.navigation.route.SplashScreenRoute
import com.gm.ai.guidebook.ui.navigation.route.StepsScreenRoute
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuideApp()
        }
    }

    @Composable
    private fun GuideApp() {
        val view = LocalView.current
        val backgroundColor = GuideTheme.palette.background
        val surfaceColor = GuideTheme.palette.surface
        val systemDarkModeEnabled = isSystemInDarkTheme()
        val controller = rememberNavController()

        val darkModeEnabled by viewModel.darkModeFlow
            .collectAsStateWithLifecycle(initialValue = systemDarkModeEnabled)

        val currentEntry by controller
            .currentBackStackEntryFlow
            .collectAsStateWithLifecycle(initialValue = null)

        val sessionStatus by viewModel.sessionStatusFlow
            .collectAsStateWithLifecycle(initialValue = SessionStatus(false))

        var dialogVisible by remember { mutableStateOf(false) }
        val areBarsVisible by remember {
            derivedStateOf { checkAreBarsVisible(currentEntry?.destination?.route) }
        }

        GuideBookTheme(darkModeEnabled ?: systemDarkModeEnabled) {
            GuideAppContent(
                currentRoute = currentEntry?.destination?.route,
                controller = controller,
                areBarsVisible = areBarsVisible,
            )
        }

        if (dialogVisible) {
            SessionExpiredDialog {
                dialogVisible = false
                controller.navigatePopUpInclusive(
                    route = LoginScreenRoute.route,
                    popUpRoute = currentEntry?.destination?.route ?: ""
                )
            }
        }

        LaunchedEffect(
            key1 = sessionStatus,
        ) {
            if (
                sessionStatus.alive ||
                !checkSessionExpiredDialogRoute(currentEntry?.destination?.route)
            ) return@LaunchedEffect
            dialogVisible = true
        }

        LaunchedEffect(Unit) {
            viewModel.saveSystemDarkMode(systemDarkModeEnabled)
        }

        LaunchedEffect(
            key1 = areBarsVisible,
            key2 = darkModeEnabled,
        ) {
            val window = this@MainActivity.window
            val color = if (areBarsVisible) surfaceColor else backgroundColor
            window.statusBarColor = color.toArgb()
            window.navigationBarColor = color.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !(darkModeEnabled ?: systemDarkModeEnabled)
                isAppearanceLightNavigationBars = !(darkModeEnabled ?: systemDarkModeEnabled)
            }
        }
    }
}

@Composable
private fun GuideAppContent(
    currentRoute: String?,
    controller: NavHostController,
    areBarsVisible: Boolean,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideTheme.palette.background),
        topBar = {
            AnimatedTopBar(
                modifier = Modifier,
                visible = areBarsVisible,
                closeVisible = currentRoute == StepsScreenRoute.routeWithArgs,
                closeClicked = { controller.popBackStack() },
            )
        },
        content = {
            GuideNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GuideTheme.palette.background)
                    .padding(it),
                controller = controller,
            )
        },
        bottomBar = {
            AnimateBottomNavBar(
                modifier = Modifier,
                controller = controller,
                visible = areBarsVisible,
            )
        },
    )
}

private fun checkSessionExpiredDialogRoute(
    currentRoute: String?,
): Boolean {
    return currentRoute != null &&
            currentRoute != SplashScreenRoute.route &&
            currentRoute != LoginScreenRoute.route
}

private fun checkAreBarsVisible(
    currentRoute: String?,
): Boolean {
    return currentRoute != null &&
            currentRoute != SplashScreenRoute.route &&
            currentRoute != LoginScreenRoute.route
}
