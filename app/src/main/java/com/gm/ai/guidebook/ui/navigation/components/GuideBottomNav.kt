package com.gm.ai.guidebook.ui.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.core.android.extensions.navigatePopUpInclusive
import com.gm.ai.guidebook.core.common.FIVE_HUNDRED_MILLIS
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

@Composable
fun AnimateBottomNavBar(
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
private fun GuideBottomNavigation(
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
            .height(
                dimensionResource(R.dimen.bottom_nav_bar_height)
            )
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
                    val currentRoute = currentDestination?.route ?: return@GuideBottomNavItem
                    navController.navigatePopUpInclusive(
                        route = route.route,
                        popUpRoute = currentRoute,
                    )
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
