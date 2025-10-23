package com.sergokuzneczow.pixels2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.reflect.KClass

@Composable
internal fun rememberPixelsState(
    networkMonitor: Flow<Boolean>,
    saveService: (picturePath: String) -> Unit = {},
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
            savePictureService = saveService,
        )
    }
}

@Stable
internal class PixelsState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: Flow<Boolean>,
    val savePictureService: (picturePath: String) -> Unit = {},
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

    val isOffline: StateFlow<Boolean> = networkMonitor.map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}