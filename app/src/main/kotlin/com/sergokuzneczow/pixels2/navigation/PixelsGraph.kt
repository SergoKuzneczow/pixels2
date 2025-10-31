package com.sergokuzneczow.pixels2.navigation

import android.content.Intent
import android.net.Uri
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
    onShowNotification: (chanelId: String, intent: Intent, title: String, message: String) -> Unit,
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    onSavePicture: (String, (Result<Uri>) -> Unit) -> Unit,
    popBackStack: () -> Unit,
    backMainMenu: () -> Unit,
    navigateToMainMenuDestination: (navOptions: NavOptions?) -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
    navigateToBottomSheetPageFilterDestination: (pageKey: Long, navOptions: NavOptions?) -> Unit,
    navigateToSelectedPictureDestination: (pictureKey: String) -> Unit,
    navigateToBottomSheetPictureInfoDestination: (pictureKey: String, navOptions: NavOptions?) -> Unit,
) {
    splashScreenDestination(
        onChangeProgressBar = onChangeProgressBar,
        navigateToMainMenu = navigateToMainMenuDestination
    )
    mainMenuScreenDestination(
        onShowSnackbar = onShowSnackbar,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
    suitablePicturesScreenDestination(
        onShowSnackbar = onShowSnackbar,
        navigateToDialogPageFilterDestination = navigateToBottomSheetPageFilterDestination,
        navigateToSelectedPictureDestination = navigateToSelectedPictureDestination,
        backMainMenu = backMainMenu,
    )
    dialogPageFilterScreenDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
    )
    bottomSheetPageFilterScreenDestination(
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        popBackStack = popBackStack,
    )
    selectedPictureDestination(
        onShowSnackbar = onShowSnackbar,
        navigateToBottomSheetPictureInfoDestination = navigateToBottomSheetPictureInfoDestination
    )
    bottomSheetPictureInfoDestination(
        onShowSnackbar = onShowSnackbar,
        onShowNotification = onShowNotification,
        onSavePicture = onSavePicture,
        navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        popBackStack = popBackStack,
    )
}