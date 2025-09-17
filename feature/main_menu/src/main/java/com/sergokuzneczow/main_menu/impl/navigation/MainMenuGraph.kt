package com.sergokuzneczow.main_menu.impl.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import com.sergokuzneczow.home.api.navigationHomeBaseRoute
import com.sergokuzneczow.settings.api.settingsScreenDestination
import com.sergokuzneczow.suitable_pictures.api.composableSuitablePicturesRoute

internal fun NavGraphBuilder.mainMenuGraph(
    titleState: MutableState<String>,
    showProgressBar: (visible: Boolean) -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    this.navigationHomeBaseRoute(
        suitablePicturesDestination = {
            this.composableSuitablePicturesRoute(
                titleState = titleState,
                showProgressBar = showProgressBar,
            )
        },
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        titleState = titleState,
        showProgressBar = showProgressBar,
    )
    settingsScreenDestination(
        titleState = titleState,
        showProgressBar = showProgressBar,
    )
}