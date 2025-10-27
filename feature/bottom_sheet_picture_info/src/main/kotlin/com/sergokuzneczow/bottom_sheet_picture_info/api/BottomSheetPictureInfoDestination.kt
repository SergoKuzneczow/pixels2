package com.sergokuzneczow.bottom_sheet_picture_info.api

import android.content.Intent
import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.sergokuzneczow.bottom_sheet_picture_info.impl.BottomSheetPictureInfoRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data class BottomSheetPictureInfoRoute(public val pictureKey: String)

public fun NavHostController.navigateToBottomSheetPictureInfoDestination(pictureKey: String, navOptions: NavOptions? = null) {
    this.navigate(route = BottomSheetPictureInfoRoute(pictureKey), navOptions = navOptions)
}

public fun NavGraphBuilder.bottomSheetPictureInfoDestination(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    onShowNotification: (chanelId: String, intent: Intent, title: String, message: String) -> Unit,
    onSavePicture: (String, (Result<Uri>) -> Unit) -> Unit,
    popBackStack: () -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    dialog<BottomSheetPictureInfoRoute> { backStackEntry ->
        val data: BottomSheetPictureInfoRoute = backStackEntry.toRoute()
        BottomSheetPictureInfoRootScreen(
            pictureKey = data.pictureKey,
            onShowSnackbar = onShowSnackbar,
            onShowNotification = onShowNotification,
            onSavePicture = onSavePicture,
            navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
            popBackStack = popBackStack,
        )
    }
}