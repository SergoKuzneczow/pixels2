package com.sergokuzneczow.main_menu.impl.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import com.sergokuzneczow.home.api.navigationHomeBaseRoute
import com.sergokuzneczow.settings.api.settingsScreenDestination
import com.sergokuzneczow.suitable_pictures.api.composableSuitablePicturesRoute

internal fun NavGraphBuilder.mainMenuGraph(
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    this.navigationHomeBaseRoute(
        suitablePicturesDestination = {
            this.composableSuitablePicturesRoute(
                titleTextState = titleTextState,
                progressBarIsVisible = progressBarIsVisible,
            )
        },
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
    )
    settingsScreenDestination(
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
    )
}