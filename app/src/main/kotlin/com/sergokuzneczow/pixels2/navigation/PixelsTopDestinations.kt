package com.sergokuzneczow.pixels2.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.home.navigation.HomeScreenBaseRoute
import com.sergokuzneczow.home.navigation.HomeScreenRoute
import com.sergokuzneczow.settings.navigation.SettingsScreenBaseRoute
import com.sergokuzneczow.settings.navigation.SettingsScreenRoute
import kotlin.reflect.KClass

internal enum class PixelsTopDestinations(
    @StringRes val titleTextId: Int,
    val icon: ImageVector,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    HOME(
        icon = PixelsIcons.home,
        titleTextId = com.sergokuzneczow.home.R.string.feature_home_title,
        route = HomeScreenRoute::class,
        baseRoute = HomeScreenBaseRoute::class,
    ),
    SETTINGS(
        icon = PixelsIcons.settings,
        titleTextId = com.sergokuzneczow.settings.R.string.feature_settings_title,
        route = SettingsScreenRoute::class,
        baseRoute = SettingsScreenBaseRoute::class,
    ),
}