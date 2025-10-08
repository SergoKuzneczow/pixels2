package com.sergokuzneczow.main_menu.impl.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import com.sergokuzneczow.home.api.composableHomeDestination
import com.sergokuzneczow.search_suitable_pictures.api.composableSearchSuitablePicturesDestination
import com.sergokuzneczow.settings.api.composableSettingsScreenDestination

internal fun NavGraphBuilder.mainMenuGraph(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    composableHomeDestination(
        onShowSnackbar = onShowSnackbar,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
    )
    composableSettingsScreenDestination(
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
    )
    composableSearchSuitablePicturesDestination(
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
}