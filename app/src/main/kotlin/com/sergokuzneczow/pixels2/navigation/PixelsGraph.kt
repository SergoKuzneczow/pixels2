package com.sergokuzneczow.pixels2.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.sergokuzneczow.dialog_page_filter.api.dialogPageFilterDestination
import com.sergokuzneczow.main_menu.api.mainMenuRouteDestination
import com.sergokuzneczow.splash.api.splashScreenDestination
import com.sergokuzneczow.suitable_pictures.api.suitablePicturesDestination

internal fun NavGraphBuilder.pixelsGraph(
    navigateToMainMenuDestination: (NavOptions?) -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
    navigateToDialogPageFilterDestination: (pageKey: Long) -> Unit,
) {
    splashScreenDestination(
        navigateToMainMenu = navigateToMainMenuDestination
    )
    mainMenuRouteDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
    suitablePicturesDestination(
        navigateToDialogPageFilterDestination = navigateToDialogPageFilterDestination,
    )
    dialogPageFilterDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
}