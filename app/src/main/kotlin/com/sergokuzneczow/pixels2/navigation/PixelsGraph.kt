package com.sergokuzneczow.pixels2.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.sergokuzneczow.bottom_sheet_page_filter.api.bottomSheetPageFilterScreenDestination
import com.sergokuzneczow.bottom_sheet_picture_info.api.bottomSheetPictureInfoDestination
import com.sergokuzneczow.dialog_page_filter.api.dialogPageFilterScreenDestination
import com.sergokuzneczow.main_menu.api.mainMenuScreenDestination
import com.sergokuzneczow.selected_picture.api.selectedPictureDestination
import com.sergokuzneczow.splash.api.splashScreenDestination
import com.sergokuzneczow.suitable_pictures.api.suitablePicturesScreenDestination

internal fun NavGraphBuilder.pixelsGraph(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    popBackStack: () -> Unit,
    navigateToMainMenuDestination: (navOptions: NavOptions?) -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
    navigateToDialogPageFilterDestination: (pageKey: Long) -> Unit,
    navigateToBottomSheetPageFilterDestination: (pageKey: Long, navOptions: NavOptions?) -> Unit,
    navigateToSelectedPictureDestination: (pictureKey: String) -> Unit,
    navigateToBottomSheetPictureInfoDestination: (pictureKey: String, navOptions: NavOptions?) -> Unit,
) {
    splashScreenDestination(
        navigateToMainMenu = navigateToMainMenuDestination
    )
    mainMenuScreenDestination(
        onShowSnackbar = onShowSnackbar,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
    suitablePicturesScreenDestination(
        onShowSnackbar = onShowSnackbar,
        //navigateToDialogPageFilterDestination = navigateToDialogPageFilterDestination,
        navigateToDialogPageFilterDestination = navigateToBottomSheetPageFilterDestination,
        navigateToSelectedPictureDestination = navigateToSelectedPictureDestination,
    )
    dialogPageFilterScreenDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
    //dialogPageFilterScreenDestination(
    //    navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    //)
    bottomSheetPageFilterScreenDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        popBackStack = popBackStack,
    )
    selectedPictureDestination(
        //navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        navigateToBottomSheetPictureInfoDestination = navigateToBottomSheetPictureInfoDestination
    )
    bottomSheetPictureInfoDestination(
        onShowSnackbar = onShowSnackbar,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        popBackStack = popBackStack,
    )
}