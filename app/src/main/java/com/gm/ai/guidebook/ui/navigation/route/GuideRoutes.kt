package com.gm.ai.guidebook.ui.navigation.route

import com.gm.ai.guidebook.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 *
 * Screens
 *
 */

object SplashScreenRoute : GuideRoute {
    override val route: String = "splash"
}

object HomeScreenRoute : BottomNavGuideRoute {
    override val route: String = "home"
    override val titleResId: Int = R.string.home
    override val iconResId: Int = R.drawable.ic_home
}

object DetailsScreenRoute : GuideRoute {
    override val route: String = "details"
}

object FavoritesScreenRoute : BottomNavGuideRoute {
    override val route: String = "favorites"
    override val titleResId: Int = R.string.favorites
    override val iconResId: Int = R.drawable.ic_like
}

object SettingsScreenRoute : BottomNavGuideRoute {
    override val route: String = "settings"
    override val titleResId: Int = R.string.settings
    override val iconResId: Int = R.drawable.ic_settings
}

object LoginScreenRoute : GuideRoute {
    override val route: String = "login"
}
