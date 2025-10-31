package com.sergokuzneczow.pixels2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.SavedState
import com.sergokuzneczow.main_menu.api.MainMenuRoute
import com.sergokuzneczow.splash.api.SplashScreenRoute
import com.sergokuzneczow.utilities.logger.log
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

@Composable
internal fun rememberPixelsState(
    applicationNotificationChanelId: String,
    darkThemeMonitor: State<Boolean?>,
    networkMonitor: State<Boolean>,
    toastMonitor: State<String?>,
    progressMonitor: State<Boolean>,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): PixelsState {
    return remember(
        navController,
        coroutineScope,
        networkMonitor,
        toastMonitor,
        applicationNotificationChanelId,
        darkThemeMonitor,
        progressMonitor,
    ) {
        PixelsState(
            navController = navController,
            coroutineScope = coroutineScope,
            applicationNotificationChanelId = applicationNotificationChanelId,
            darkThemeMonitor = darkThemeMonitor,
            networkMonitor = networkMonitor,
            toastMonitor = toastMonitor,
            progressMonitor = progressMonitor,
        )
    }
}

@Stable
internal class PixelsState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val applicationNotificationChanelId: String,
    darkThemeMonitor: State<Boolean?>,
    progressMonitor: State<Boolean>,
    networkMonitor: State<Boolean>,
    toastMonitor: State<String?>,
) {

    init {
        navController.addOnDestinationChangedListener(object : OnDestinationChangedListener {
            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: SavedState?) {
                if (destination.hasRoute(MainMenuRoute::class)) {
                    log(tag = "PixelsState") { "addOnDestinationChangedListener(); destination=MainMenuRoute" }
                    mainMenuDestination = destination
                }
            }
        })
    }

    var mainMenuDestination: NavDestination? = null
        private set

    val startDestination: KClass<*> = SplashScreenRoute::class

    val isOnline: State<Boolean> = networkMonitor

    val isDark: State<Boolean?> = darkThemeMonitor

    val isProgress: State<Boolean> = progressMonitor

    val toast: State<String?> = toastMonitor
}