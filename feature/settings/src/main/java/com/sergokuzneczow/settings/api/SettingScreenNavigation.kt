package com.sergokuzneczow.settings.api

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sergokuzneczow.settings.impl.SettingsScreenRoot
import kotlinx.serialization.Serializable

@Serializable
public data object SettingsScreenRoute

@Serializable
public data object SettingsScreenBaseRoute

public fun NavController.navigateToSettingsScreenDestination(navOptions: NavOptions) {
    this.navigate(route = SettingsScreenRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.settingsScreenDestination(
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
) {
    navigation<SettingsScreenBaseRoute>(
        startDestination = SettingsScreenRoute,
    ) {
        composable<SettingsScreenRoute> {
            SettingsScreenRoot(
                titleTextState = titleTextState,
                progressBarIsVisible = progressBarIsVisible
            )
        }
    }
}