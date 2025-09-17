package com.sergokuzneczow.pixels2.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.sergokuzneczow.main_menu.api.MainMenuRoute
import com.sergokuzneczow.main_menu.api.navigateToMainMenuDestination
import com.sergokuzneczow.pixels2.navigation.PixelsState
import com.sergokuzneczow.pixels2.navigation.pixelsGraph

@Composable
internal fun PixelsNavHost(
    applicationState: PixelsState,
//    changeTitle: (title: String) -> Unit,
//    showProgressBar: (visible: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = applicationState.navController,
        startDestination = applicationState.startDestination,
        modifier = modifier,
    ) {
//
//        val navOptions = navOptions {
//            popUpTo<MainMenuRoute> {
//                saveState = true
//                inclusive = true
//            }
//            launchSingleTop = true
//            restoreState = true
//        }

        this.pixelsGraph(
            navigateToMainMenuDestination = {navOptions-> applicationState.navController.navigateToMainMenuDestination(navOptions) },
        )
    }
}