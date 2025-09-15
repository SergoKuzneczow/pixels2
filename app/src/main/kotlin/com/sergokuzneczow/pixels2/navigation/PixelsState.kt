package com.sergokuzneczow.pixels2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sergokuzneczow.home.navigation.navigateToHomeScreenDestination
import com.sergokuzneczow.settings.navigation.navigateToSettingsScreenDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
internal fun rememberPixelsState(
    networkMonitor: Flow<Boolean>,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): PixelsState {
    return remember(
        navController,
        coroutineScope,
        networkMonitor,
    ) {
        PixelsState(
            navController = navController,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
        )
    }
}

@Stable
internal class PixelsState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: Flow<Boolean>,
) {
    private val previousDestination: MutableState<NavDestination?> = mutableStateOf(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry: State<NavBackStackEntry?> = navController.currentBackStackEntryFlow.collectAsState(initial = null)
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: PixelsTopDestinations?
        @Composable get() {
            return PixelsTopDestinations.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }

    val isOffline = networkMonitor.map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val topLevelDestinations: List<PixelsTopDestinations> = PixelsTopDestinations.entries

    val startDestination: PixelsTopDestinations = PixelsTopDestinations.HOME

    fun navigateToTopLevelDestination(topLevelDestination: PixelsTopDestinations) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
            when (topLevelDestination) {
                PixelsTopDestinations.HOME -> navController.navigateToHomeScreenDestination(topLevelNavOptions)
                PixelsTopDestinations.SETTINGS -> navController.navigateToSettingsScreenDestination(topLevelNavOptions)
            }
        }
    }
}