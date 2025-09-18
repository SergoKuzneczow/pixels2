package com.sergokuzneczow.home.api

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sergokuzneczow.home.impl.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
public data object HomeRoute

@Serializable
public data object HomeBaseRoute

public fun NavController.navigateToHomeRoute(navOptions: NavOptions) {
    this.navigate(route = HomeRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.navigationHomeBaseRoute(
    suitablePicturesDestination: NavGraphBuilder.() -> Unit,
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    navigation<HomeBaseRoute>(
        startDestination = HomeRoute,
    ) {
        composable<HomeRoute> {
            HomeScreenRoot(
                progressBarIsVisible = progressBarIsVisible,
                titleTextState = titleTextState,
                navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination
            )
        }
        suitablePicturesDestination.invoke(this)
    }
}