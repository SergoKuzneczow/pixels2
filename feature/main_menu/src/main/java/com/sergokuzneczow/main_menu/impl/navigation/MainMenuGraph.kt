package com.sergokuzneczow.main_menu.impl.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import com.sergokuzneczow.home.api.composableHomeDestination
import com.sergokuzneczow.search_suitable_pictures.api.composableSearchSuitablePicturesDestination
import com.sergokuzneczow.settings.api.composableSettingsScreenDestination

internal fun NavGraphBuilder.mainMenuGraph(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    titleTextState: MutableState<String>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    composableHomeDestination(
        onShowSnackbar = onShowSnackbar,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        titleTextState = titleTextState,
    )
    composableSettingsScreenDestination(
        titleTextState = titleTextState,
    )
    composableSearchSuitablePicturesDestination(
        titleTextState = titleTextState,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
}