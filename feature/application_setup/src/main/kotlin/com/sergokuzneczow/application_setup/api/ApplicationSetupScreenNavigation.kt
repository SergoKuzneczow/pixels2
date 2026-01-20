package com.sergokuzneczow.application_setup.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sergokuzneczow.application_setup.impl.ApplicationSetupRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data object ApplicationSetupScreenRoute

public fun NavHostController.navigateToApplicationSetupScreenDestination(navOptions: NavOptions? = null) {
    this.navigate(route = ApplicationSetupScreenRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.applicationSetupScreenDestination(navigateToMainMenu: (NavOptions?) -> Unit, ) {
    composable<ApplicationSetupScreenRoute> {
        ApplicationSetupRootScreen(navigateToMainMenu)
    }
}