package com.sergokuzneczow.pixels2.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.sergokuzneczow.bottom_sheet_page_filter.api.navigateToBottomSheetPageFilterDestination
import com.sergokuzneczow.bottom_sheet_picture_info.api.navigateToBottomSheetPictureInfoDestination
import com.sergokuzneczow.main_menu.api.navigateToMainMenuDestination
import com.sergokuzneczow.pixels2.PixelsState
import com.sergokuzneczow.pixels2.navigation.pixelsGraph
import com.sergokuzneczow.selected_picture.api.navigateToSelectedPictureDestination
import com.sergokuzneczow.suitable_pictures.api.navigateToSuitablePicturesRoute

@Composable
internal fun PixelsNavHost(
    applicationState: PixelsState,
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    onShowNotification: (chanelId: String, intent: Intent, title: String, message: String) -> Unit,
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    onSavePicture: (String, (Result<Uri>) -> Unit) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = applicationState.navController,
        startDestination = applicationState.startDestination,
        modifier = modifier,
    ) {
        this.pixelsGraph(
            onShowSnackbar = onShowSnackbar,
            onShowNotification = onShowNotification,
            onChangeProgressBar = onChangeProgressBar,
            onSavePicture = onSavePicture,
            popBackStack = { applicationState.navController.popBackStack() },
            backMainMenu = { applicationState.mainMenuDestination?.let { applicationState.navController.popBackStack(route = it.route!!, inclusive = false, saveState = true) } },
            navigateToMainMenuDestination = { navOptions: NavOptions? -> applicationState.navController.navigateToMainMenuDestination(navOptions) },
            navigateToSuitablePicturesDestination = { pageKey: Long -> applicationState.navController.navigateToSuitablePicturesRoute(pageKey) },
            navigateToSelectedPictureDestination = { pictureKey: String -> applicationState.navController.navigateToSelectedPictureDestination(pictureKey) },
            navigateToBottomSheetPageFilterDestination = { pageKey: Long, navOptions: NavOptions? ->
                applicationState.navController.navigateToBottomSheetPageFilterDestination(pageKey, navOptions)
            },
            navigateToBottomSheetPictureInfoDestination = { pictureKey, navOptions ->
                applicationState.navController.navigateToBottomSheetPictureInfoDestination(pictureKey, navOptions)
            }
        )
    }
}