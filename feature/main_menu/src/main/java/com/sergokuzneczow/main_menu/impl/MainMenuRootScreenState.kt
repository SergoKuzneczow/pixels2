package com.sergokuzneczow.main_menu.impl

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.home.api.HomeRoute
import com.sergokuzneczow.search_suitable_pictures.api.SearchSuitablePicturesRoute
import com.sergokuzneczow.settings.api.SettingsScreenRoute
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

@Composable
internal fun rememberMainMenuRootScreenState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): MainMenuRootScreenState {
    return remember(
        navController,
        coroutineScope,
    ) {
        MainMenuRootScreenState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
internal class MainMenuRootScreenState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {

    private val previousDestination: MutableState<NavDestination?> = mutableStateOf(null)

    val startDestination: KClass<*> = MainMenuTopDestination.HOME.baseRoute

    val topLevelDestinations: List<MainMenuTopDestination> = MainMenuTopDestination.entries

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow.collectAsState(initial = null)
            val destination = currentEntry.value?.destination
            if (destination != null) previousDestination.value = destination

            return destination
        }

    fun findStartDestination(): NavDestination = navController.graph.findStartDestination()
}

internal enum class MainMenuTopDestination(
    @StringRes val titleTextId: Int,
    val icon: ImageVector,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    HOME(
        icon = PixelsIcons.home,
        titleTextId = com.sergokuzneczow.home.R.string.feature_home_title,
        route = HomeRoute::class,
    ),
    SEARCH(
        icon = PixelsIcons.search,
        titleTextId = com.sergokuzneczow.settings.R.string.feature_settings_title,
        route = SearchSuitablePicturesRoute::class,
    ),
    SETTINGS(
        icon = PixelsIcons.settings,
        titleTextId = com.sergokuzneczow.settings.R.string.feature_settings_title,
        route = SettingsScreenRoute::class,
    ),
}