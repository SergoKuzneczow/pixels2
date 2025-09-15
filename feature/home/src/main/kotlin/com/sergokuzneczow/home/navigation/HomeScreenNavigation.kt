package com.sergokuzneczow.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sergokuzneczow.home.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
public data object HomeScreenRoute

@Serializable
public data object HomeScreenBaseRoute

public fun NavController.navigateToHomeScreenDestination(navOptions: NavOptions) {
    this.navigate(route = HomeScreenRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.homeScreenDestination(
    changeTitle: (title: String) -> Unit,
    showProgressBar: (visible: Boolean) -> Unit,
) {
    navigation<HomeScreenBaseRoute>(
        startDestination = HomeScreenRoute,
    ) {
        composable<HomeScreenRoute> {
            HomeScreenRoot(
                showProgressBar = showProgressBar,
                changeTitle = changeTitle
            )
        }
    }
}