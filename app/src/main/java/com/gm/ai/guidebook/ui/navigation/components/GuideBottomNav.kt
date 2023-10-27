package com.gm.ai.guidebook.ui.navigation.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gm.ai.guidebook.core.android.extensions.clickableWithoutRipple
import com.gm.ai.guidebook.core.android.extensions.navigateSingleTopTo
import com.gm.ai.guidebook.ui.navigation.route.BottomNavGuideRoute
import com.gm.ai.guidebook.ui.navigation.route.FavoritesScreenRoute
import com.gm.ai.guidebook.ui.navigation.route.HomeScreenRoute
import com.gm.ai.guidebook.ui.navigation.route.SettingsScreenRoute
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

private val bottomNavItems = listOf(
    HomeScreenRoute,
    FavoritesScreenRoute,
    SettingsScreenRoute,
)

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun GuideBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var selectedItem: BottomNavGuideRoute? by remember { mutableStateOf(bottomNavItems.first()) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(72.dp)
            .fillMaxWidth()
            .background(GuideTheme.palette.surface),
    ) {
        Spacer(modifier = Modifier.width(GuideTheme.offset.tiny))
        bottomNavItems.forEach { route ->
            GuideBottomNavItem(
                iconResId = route.iconResId,
                text = stringResource(route.titleResId),
                selected = selectedItem?.route == route.route,
                onClick = {
                    if (route.route == currentDestination?.route) return@GuideBottomNavItem
                    navController.navigateSingleTopTo(route = route.route)
                },
            )
        }
        Spacer(modifier = Modifier.width(GuideTheme.offset.tiny))
    }

    LaunchedEffect(key1 = currentDestination) {
        val currentRoute = currentDestination?.route
        val currentItem = bottomNavItems.find { it.route == currentRoute }
        if (selectedItem == currentItem) return@LaunchedEffect
        selectedItem = currentItem
    }
}

@Composable
fun GuideBottomNavItem(
    modifier: Modifier = Modifier,
    iconResId: Int,
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val color = if (selected) {
        GuideTheme.palette.primary
    } else {
        GuideTheme.palette.onBackground.copy(alpha = 0.4f)
    }
    val animatedColor by animateColorAsState(targetValue = color, label = "")

    Column(
        modifier = modifier
            .size(64.dp)
            .clickableWithoutRipple(onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = animatedColor,
        )
        Text(
            text = text,
            style = GuideTheme.typography.bodySmall,
            color = animatedColor,
        )
    }
}
