package com.sergokuzneczow.pixels2.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.sergokuzneczow.dialog_page_filter.api.dialogPageFilterScreenDestination
import com.sergokuzneczow.main_menu.api.mainMenuScreenDestination
import com.sergokuzneczow.selected_picture.api.selectedPictureDestination
import com.sergokuzneczow.splash.api.splashScreenDestination
import com.sergokuzneczow.suitable_pictures.api.suitablePicturesScreenDestination

internal fun NavGraphBuilder.pixelsGraph(
    navigateToMainMenuDestination: (NavOptions?) -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
    navigateToDialogPageFilterDestination: (pageKey: Long) -> Unit,
    navigateToSelectedPictureDestination: (pictureKey: String) -> Unit,
) {
    splashScreenDestination(
        navigateToMainMenu = navigateToMainMenuDestination
    )
    mainMenuScreenDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
    suitablePicturesScreenDestination(
        navigateToDialogPageFilterDestination = navigateToDialogPageFilterDestination,
        navigateToSelectedPictureDestination = navigateToSelectedPictureDestination,
    )
    dialogPageFilterScreenDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
    selectedPictureDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
}