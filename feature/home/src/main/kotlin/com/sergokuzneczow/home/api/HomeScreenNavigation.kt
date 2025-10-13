package com.sergokuzneczow.home.api

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sergokuzneczow.home.impl.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
public data object HomeScreenRoute

public fun NavController.navigateToHomeDestination(navOptions: NavOptions) {
    this.navigate(route = HomeScreenRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.composableHomeDestination(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    titleTextState: MutableState<String>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    composable<HomeScreenRoute> {
        HomeScreenRoot(
            onShowSnackbar = onShowSnackbar,
            titleTextState = titleTextState,
            navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination
        )
    }
}