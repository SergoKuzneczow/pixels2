package com.sergokuzneczow.splash.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sergokuzneczow.splash.impl.SplashRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data object SplashScreenRoute

public fun NavHostController.navigateToSplashScreenDestination(navOptions: NavOptions? = null) {
    this.navigate(route = SplashScreenRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.splashScreenDestination(
    navigateToMainMenu: (NavOptions?) -> Unit
) {
    composable<SplashScreenRoute> {
        SplashRootScreen(
            navigateToMainMenu = navigateToMainMenu
        )
    }
}