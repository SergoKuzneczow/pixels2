package com.sergokuzneczow.pixels2.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.sergokuzneczow.dialog_page_filter.api.navigateToDialogPageFilterDestination
import com.sergokuzneczow.main_menu.api.navigateToMainMenuDestination
import com.sergokuzneczow.pixels2.PixelsState
import com.sergokuzneczow.pixels2.navigation.pixelsGraph
import com.sergokuzneczow.suitable_pictures.api.navigateToSuitablePicturesRoute

@Composable
internal fun PixelsNavHost(
    applicationState: PixelsState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = applicationState.navController,
        startDestination = applicationState.startDestination,
        modifier = modifier,
    ) {
        this.pixelsGraph(
            navigateToMainMenuDestination = { navOptions: NavOptions? -> applicationState.navController.navigateToMainMenuDestination(navOptions) },
            navigateToSuitablePicturesDestination = { pageKey: Long -> applicationState.navController.navigateToSuitablePicturesRoute(pageKey) },
            navigateToDialogPageFilterDestination = { pageKey: Long -> applicationState.navController.navigateToDialogPageFilterDestination(pageKey) }
        )
    }
}