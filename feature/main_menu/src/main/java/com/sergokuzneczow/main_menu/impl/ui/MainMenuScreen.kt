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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sergokuzneczow.core.system_components.PixelsNavigationSuiteScaffold
import com.sergokuzneczow.core.system_components.PixelsTopBar
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.home.api.navigateToHomeDestination
import com.sergokuzneczow.main_menu.impl.MainMenuRootScreenState
import com.sergokuzneczow.main_menu.impl.MainMenuTopDestination
import com.sergokuzneczow.main_menu.impl.rememberMainMenuRootScreenState
import com.sergokuzneczow.search_suitable_pictures.api.navigateToSearchSuitablePicturesDestination
import com.sergokuzneczow.settings.api.navigateToSettingsScreenDestination
import kotlin.reflect.KClass

@Composable
internal fun MainMenuScreen(
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    navigateToSuitablePicturesDestination: (Long) -> Unit,
    rootScreenState: MainMenuRootScreenState = rememberMainMenuRootScreenState(),
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val titleTextState: MutableState<String> = remember { mutableStateOf("Default") }
    val currentDestination: NavDestination? = rootScreenState.currentDestination

    PixelsNavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize(),
        navigationSuiteItems = {
            MainMenuTopDestination.entries.forEach { destination ->
                item(
                    selected = currentDestination.isRouteInHierarchy(destination.route),
                    onClick = {
                        val navOptions: NavOptions = navOptions {
                            popUpTo(rootScreenState.startDestination) {
                                saveState = true
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        when (destination) {
                            MainMenuTopDestination.HOME -> rootScreenState.navController.navigateToHomeDestination(navOptions)
                            MainMenuTopDestination.SETTINGS -> rootScreenState.navController.navigateToSettingsScreenDestination(navOptions)
                            MainMenuTopDestination.SEARCH -> rootScreenState.navController.navigateToSearchSuitablePicturesDestination(navOptions)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
                            modifier = Modifier.size(Dimensions.IconSize)
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = null,
                            modifier = Modifier.size(Dimensions.IconSize)
                        )
                    },
                    label = { Text(stringResource(destination.titleTextId)) },
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
        content = {
            MainMenuNavHost(
                onChangeProgressBar = onChangeProgressBar,
                onShowSnackbar = onShowSnackbar,
                rootScreenState = rootScreenState,
                titleTextState = titleTextState,
                navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
                modifier = Modifier.fillMaxSize()
            )
            PixelsTopBar(title = titleTextState.value)
        }
    )
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false