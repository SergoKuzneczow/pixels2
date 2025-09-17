package com.sergokuzneczow.pixels2.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.sergokuzneczow.main_menu.api.mainMenuRouteDestination
import com.sergokuzneczow.splash.navgiation.splashScreenDestination

internal fun NavGraphBuilder.pixelsGraph(
    navigateToMainMenuDestination: (NavOptions?) -> Unit,
) {
    splashScreenDestination(
        navigateToMainMenu = navigateToMainMenuDestination
    )
    mainMenuRouteDestination()
}