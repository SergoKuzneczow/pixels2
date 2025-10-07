package com.sergokuzneczow.main_menu.impl.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.navOptions
import com.sergokuzneczow.core.system_components.PixelsNavigationSuiteScaffold
import com.sergokuzneczow.core.system_components.PixelsTopBar
import com.sergokuzneczow.main_menu.impl.MainMenuRootScreenState
import com.sergokuzneczow.main_menu.impl.MainMenuTopDestination
import com.sergokuzneczow.main_menu.impl.rememberMainMenuRootScreenState
import com.sergokuzneczow.search_suitable_pictures.api.navigateToSearchSuitablePicturesDestination
import com.sergokuzneczow.settings.api.navigateToSettingsScreenDestination
import com.sergokuzneczow.suitable_pictures.api.SuitablePicturesRoute
import kotlin.reflect.KClass

@Composable
internal fun MainMenuScreen(
    navigateToSuitablePicturesDestination: (Long) -> Unit,
    rootScreenState: MainMenuRootScreenState = rememberMainMenuRootScreenState(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val titleTextState: MutableState<String> = remember { mutableStateOf("Default") }
    val progressBarIsVisible: MutableState<Boolean> = remember { mutableStateOf(true) }
    val currentDestination = rootScreenState.currentDestination

    PixelsNavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize(),
        navigationSuiteItems = {
            MainMenuTopDestination.entries.forEach { destination ->
                item(
                    selected = currentDestination.isRouteInHierarchy(destination.route),
                    onClick = {
                        when {
                            currentDestination.isRouteInHierarchy(SuitablePicturesRoute::class) -> {
                                rootScreenState.lastHomeBlockDestination.value = currentDestination
                            }
                        }

                        val navOptions = navOptions {
                            popUpTo(rootScreenState.getLastHomeBlockDestinationOrStartDestination()) {
                                saveState = true
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                        when (destination) {
                            MainMenuTopDestination.HOME -> {
                                // Если текущий destination относится в вкладке Home, то при повторном нажатии на вкладку Home, откроется корнейвой destination для вкладки Home (HomeDestination)
                                // Если текущий destination не отнисится к вкладке Home, то при нажатии на вкладку Home будет востановлена иерархия destinations, относящихся к вкладке Home
                                if (currentDestination.isRouteInHierarchy(destination.baseRoute) || currentDestination.isRouteInHierarchy(destination.route)) {
                                    rootScreenState.navController.popBackStack(rootScreenState.findStartDestination().id, inclusive = false)
                                    rootScreenState.lastHomeBlockDestination.value = null
                                } else {
                                    rootScreenState.navController.popBackStack(rootScreenState.getLastHomeBlockDestinationOrStartDestination(), inclusive = false)
                                    rootScreenState.lastHomeBlockDestination.value = null
                                }
                            }

                            MainMenuTopDestination.SETTINGS -> rootScreenState.navController.navigateToSettingsScreenDestination(navOptions)

                            MainMenuTopDestination.SEARCH -> rootScreenState.navController.navigateToSearchSuitablePicturesDestination(navOptions)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(stringResource(destination.titleTextId)) },
                    modifier = Modifier
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
        content = {
            MainMenuNavHost(
                rootScreenState = rootScreenState,
                titleTextState = titleTextState,
                progressBarIsVisible = progressBarIsVisible,
                navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
                modifier = Modifier.fillMaxSize()
            )
            PixelsTopBar(
                title = titleTextState.value,
                visibleProgressBar = progressBarIsVisible.value,
            )
        }
    )
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false