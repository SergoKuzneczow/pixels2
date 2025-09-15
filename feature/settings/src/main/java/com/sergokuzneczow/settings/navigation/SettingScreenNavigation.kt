package com.sergokuzneczow.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sergokuzneczow.settings.SettingsScreenRoot
import kotlinx.serialization.Serializable

@Serializable
public data object SettingsScreenRoute

@Serializable
public data object SettingsScreenBaseRoute

public fun NavController.navigateToSettingsScreenDestination(navOptions: NavOptions) {
    this.navigate(route = SettingsScreenRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.settingsScreenDestination(
    changeTitle: (title: String) -> Unit,
    showProgressBar: (visible: Boolean) -> Unit,
) {
    navigation<SettingsScreenBaseRoute>(
        startDestination = SettingsScreenRoute,
    ) {
        composable<SettingsScreenRoute> {
            SettingsScreenRoot(
                showProgressBar = showProgressBar,
                changeTitle = changeTitle
            )
        }
    }
}