package com.sergokuzneczow.main_menu.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.sergokuzneczow.main_menu.impl.MainMenuRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data object MainMenuRoute

public fun NavHostController.navigateToMainMenuDestination(navOptions: NavOptions? = null) {
    this.navigate(route = MainMenuRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.mainMenuRouteDestination() {
    composable<MainMenuRoute> {
        MainMenuRootScreen()
    }
}