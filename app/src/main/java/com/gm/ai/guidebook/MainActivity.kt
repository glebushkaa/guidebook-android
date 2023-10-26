package com.gm.ai.guidebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gm.ai.guidebook.ui.navigation.GuideNavHost
import com.gm.ai.guidebook.ui.navigation.components.GuideBottomNavigation
import com.gm.ai.guidebook.ui.navigation.route.SplashScreenRoute
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuideApp()
        }
    }

    @Composable
    private fun GuideApp() {
        GuideBookTheme {
            val controller = rememberNavController()
            val currentEntry by controller.currentBackStackEntryFlow.collectAsStateWithLifecycle(
                initialValue = null,
            )
            val areBarsVisible by remember {
                derivedStateOf { currentEntry != null && currentEntry?.destination?.route != SplashScreenRoute.route }
            }
            val backgroundColor = GuideTheme.palette.background
            val surfaceColor = GuideTheme.palette.surface

            LaunchedEffect(key1 = areBarsVisible) {
                val window = this@MainActivity.window
                val color = if (areBarsVisible) surfaceColor else backgroundColor
                window.statusBarColor = color.toArgb()
            }

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GuideTheme.palette.background),
                topBar = {
                    AnimatedTopBar(
                        modifier = Modifier,
                        visible = areBarsVisible,
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
                animationSpec = tween(800),
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Bottom,
                animationSpec = tween(800),
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
    ) {
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(800),
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Top,
                animationSpec = tween(800),
            ),
        ) {
            GuideTopBar()
        }
    }

    @Composable
    @Preview
    private fun GuideTopBar(
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(GuideTheme.palette.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(start = GuideTheme.offset.regular)
                    .size(32.dp),
                painter = painterResource(id = R.drawable.img_book),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(
                    horizontal = GuideTheme.offset.medium,
                ),
                text = "GuideBook",
                style = GuideTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                ),
                color = GuideTheme.palette.onBackground,
            )
        }
    }
}
