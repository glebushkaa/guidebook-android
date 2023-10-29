package com.gm.ai.guidebook.core.android.extensions

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

fun NavController.navigatePopUpInclusive(
    route: String,
    popUpRoute: String,
) {
    navigate(route) {
        popUpTo(popUpRoute) {
            inclusive = true
            saveState = true
        }
    }
}

fun NavController.navigateSingleTopTo(
    route: String,
) {
    navigate(
        route = route,
    ) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id,
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
