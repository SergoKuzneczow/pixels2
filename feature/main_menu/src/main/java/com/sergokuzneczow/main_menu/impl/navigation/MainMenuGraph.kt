package com.sergokuzneczow.main_menu.impl.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import com.sergokuzneczow.home.api.navigationHomeBaseDestination
import com.sergokuzneczow.settings.api.settingsScreenBaseDestination

internal fun NavGraphBuilder.mainMenuGraph(
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    navigationHomeBaseDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
    )
    settingsScreenBaseDestination(
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
    )
}