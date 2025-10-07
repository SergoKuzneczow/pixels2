package com.sergokuzneczow.bottom_sheet_picture_info.api

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
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
    popBackStack: () -> Unit,
) {
    dialog<BottomSheetPictureInfoRoute> { backStackEntry ->
        val data: BottomSheetPictureInfoRoute = backStackEntry.toRoute()
        BottomSheetPictureInfoRootScreen(
            pictureKey = data.pictureKey,
            onShowSnackbar = onShowSnackbar,
            navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
            popBackStack = popBackStack,
        )
    }
}