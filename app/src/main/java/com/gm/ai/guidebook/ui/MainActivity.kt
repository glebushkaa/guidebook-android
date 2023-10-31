package com.gm.ai.guidebook.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.core.android.extensions.applyIf
import com.gm.ai.guidebook.core.android.extensions.navigatePopUpInclusive
import com.gm.ai.guidebook.core.common.FIVE_HUNDRED_MILLIS
import com.gm.ai.guidebook.domain.SessionBus
import com.gm.ai.guidebook.ui.components.GuideIconButton
import com.gm.ai.guidebook.ui.dialogs.SessionExpiredDialog
import com.gm.ai.guidebook.ui.navigation.GuideNavHost
import com.gm.ai.guidebook.ui.navigation.components.GuideBottomNavigation
import com.gm.ai.guidebook.ui.navigation.route.LoginScreenRoute
import com.gm.ai.guidebook.ui.navigation.route.SplashScreenRoute
import com.gm.ai.guidebook.ui.navigation.route.StepsScreenRoute
import com.gm.ai.guidebook.ui.screen.steps.StepsEvent
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.consumeAsFlow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 *
 * TODO refactor
 *
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
        val systemDarkModeEnabled = isSystemInDarkTheme()
        val darkModeEnabled by viewModel.darkModeFlow.collectAsStateWithLifecycle(
            initialValue = systemDarkModeEnabled,
        )

        val controller = rememberNavController()
        val currentEntry by controller.currentBackStackEntryFlow.collectAsStateWithLifecycle(
            initialValue = null,
        )
        val areBarsVisible by remember {
            derivedStateOf { checkAreBarsVisible(currentEntry) }
        }

        val sessionAlive by SessionBus.sessionAlive
            .consumeAsFlow()
            .collectAsStateWithLifecycle(initialValue = true)

        var dialogVisible by remember { mutableStateOf(false) }

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
            key1 = sessionAlive,
        ) {
            if (
                sessionAlive ||
                !checkSessionExpiredDialogRoute(currentEntry?.destination?.route)
            ) return@LaunchedEffect
            dialogVisible = true
        }

        GuideBookTheme(
            darkTheme = darkModeEnabled ?: systemDarkModeEnabled,
        )
        {
            val view = LocalView.current
            val backgroundColor = GuideTheme.palette.background
            val surfaceColor = GuideTheme.palette.surface

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GuideTheme.palette.background),
                topBar = {
                    AnimatedTopBar(
                        modifier = Modifier,
                        visible = areBarsVisible,
                        closeVisible = currentEntry?.destination?.route == StepsScreenRoute.routeWithArgs,
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

    private fun checkSessionExpiredDialogRoute(
        currentRoute: String?,
    ): Boolean {
        return currentRoute != SplashScreenRoute.route && currentRoute != LoginScreenRoute.route
    }

    private fun checkAreBarsVisible(
        currentEntry: NavBackStackEntry?,
    ): Boolean {
        return currentEntry != null &&
                currentEntry.destination.route != SplashScreenRoute.route &&
                currentEntry.destination.route != LoginScreenRoute.route
    }

    @Composable
    private fun AnimateBottomNavBar(
        modifier: Modifier = Modifier,
        controller: NavHostController,
        visible: Boolean,
    ) {
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = expandVertically(
                expandFrom = Alignment.Bottom,
                animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt()),
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Bottom,
                animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt()),
            ),
        ) {
            GuideBottomNavigation(
                navController = controller,
            )
        }
    }

    @Composable
    private fun AnimatedTopBar(
        modifier: Modifier = Modifier,
        visible: Boolean,
        closeVisible: Boolean = false,
        closeClicked: () -> Unit = {},
    ) {
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt()),
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Top,
                animationSpec = tween(FIVE_HUNDRED_MILLIS.toInt()),
            ),
        ) {
            GuideTopBar(
                closeVisible = closeVisible,
                closeClicked = closeClicked,
            )
        }
    }

    @Composable
    @Preview
    private fun GuideTopBar(
        modifier: Modifier = Modifier,
        closeClicked: () -> Unit = {},
        closeVisible: Boolean = false,
    ) {
        Row(
            modifier = modifier
                .height(
                    dimensionResource(R.dimen.top_bar_height)
                )
                .fillMaxWidth()
                .background(GuideTheme.palette.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(start = GuideTheme.offset.regular)
                    .size(
                        dimensionResource(R.dimen.top_bar_image_size)
                    ),
                painter = painterResource(id = R.drawable.img_book),
                contentDescription = null,
            )
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = GuideTheme.offset.medium,
                    )
                    .weight(1f),
                text = stringResource(R.string.app_name),
                style = GuideTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                ),
                color = GuideTheme.palette.onBackground,
            )
            AnimatedVisibility(
                modifier = Modifier.padding(end = GuideTheme.offset.regular),
                visible = closeVisible
            ) {
                GuideIconButton(
                    iconResId = R.drawable.ic_close,
                    tint = GuideTheme.palette.onBackground,
                    onClick = closeClicked,
                )
            }
        }
    }
}
