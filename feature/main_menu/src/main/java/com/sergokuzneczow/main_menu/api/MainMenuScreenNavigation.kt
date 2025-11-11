package com.sergokuzneczow.main_menu.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sergokuzneczow.main_menu.impl.MainMenuRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data object MainMenuRoute

public fun NavHostController.navigateToMainMenuDestination(navOptions: NavOptions? = null) {
    this.navigate(route = MainMenuRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.mainMenuScreenDestination(
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    composable<MainMenuRoute> {
        MainMenuRootScreen(
            onChangeProgressBar = onChangeProgressBar,
            onShowSnackbar = onShowSnackbar,
            navigateToSuitablePicturesDestination
        )
    }
}