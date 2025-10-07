package com.sergokuzneczow.main_menu.impl.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import com.sergokuzneczow.home.api.navigationHomeBaseDestination
import com.sergokuzneczow.search_suitable_pictures.api.searchSuitablePicturesDestination
import com.sergokuzneczow.settings.api.settingsScreenBaseDestination

internal fun NavGraphBuilder.mainMenuGraph(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    navigationHomeBaseDestination(
        onShowSnackbar = onShowSnackbar,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
    )
    settingsScreenBaseDestination(
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
    )
    searchSuitablePicturesDestination(
        titleTextState = titleTextState,
        progressBarIsVisible = progressBarIsVisible,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
}