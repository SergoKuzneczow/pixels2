package com.sergokuzneczow.pixels2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sergokuzneczow.splash.api.SplashScreenRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.reflect.KClass

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
    val startDestination: KClass<*> = SplashScreenRoute::class

    private val previousDestination: MutableState<NavDestination?> = mutableStateOf(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow.collectAsState(initial = null)
            val currentDestination = currentEntry.value?.destination
            if (currentDestination != null) previousDestination.value = currentDestination
            return currentDestination
            /* ?: previousDestination.value*/
        }

    val isOffline = networkMonitor.map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    fun findStartDestination(): NavDestination = navController.graph.findStartDestination()
}