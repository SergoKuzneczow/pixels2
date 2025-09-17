package com.sergokuzneczow.main_menu.impl.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.navOptions
import com.sergokuzneczow.core.system_components.PixelsScaffold
import com.sergokuzneczow.core.system_components.PixelsTopBar
import com.sergokuzneczow.main_menu.impl.MainMenuRootScreenState
import com.sergokuzneczow.main_menu.impl.MainMenuTopDestination
import com.sergokuzneczow.main_menu.impl.rememberMainMenuRootScreenState
import com.sergokuzneczow.settings.api.navigateToSettingsScreenDestination
import com.sergokuzneczow.suitable_pictures.api.SuitablePicturesRoute
import kotlin.reflect.KClass

@Composable
internal fun MainMenuScreen(
    rootScreenState: MainMenuRootScreenState = rememberMainMenuRootScreenState(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val titleState: MutableState<String> = remember { mutableStateOf("Default") }
    var visibleProgressbar: Boolean by remember { mutableStateOf(true) }
    val currentDestination = rootScreenState.currentDestination

    PixelsScaffold(
        modifier = Modifier.fillMaxSize(),
        navigationSuiteItems = {
            MainMenuTopDestination.entries.forEach { destination ->
                item(
//                    selected = (rootScreenState.currentTopLevelDestination == destination).also {
//                        log(tag = "MainMenuScreen") { "rootScreenState.currentTopLevelDestination=${rootScreenState.currentTopLevelDestination}" }
//                        log(tag = "MainMenuScreen") { "destination=$destination" }
//                    },
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
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
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
                titleState = titleState,
                showProgressBar = { visibleProgressbar = it },
                modifier = Modifier.fillMaxSize()
            )
            PixelsTopBar(
                title = titleState.value,
                visibleProgressBar = visibleProgressbar,
            )
        }
    )
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false