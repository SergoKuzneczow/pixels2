package com.sergokuzneczow.selected_picture.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.sergokuzneczow.selected_picture.impl.SelectedPictureRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data class SelectedPictureRoute(val pictureKey: String)

public fun NavHostController.navigateToSelectedPictureDestination(pictureKey: String, navOptions: NavOptions? = null) {
    this.navigate(route = SelectedPictureRoute(pictureKey), navOptions = navOptions)
}

public fun NavGraphBuilder.selectedPictureDestination(
    navigateToBottomSheetPictureInfoDestination: (pageKey: String, navOptions: NavOptions?) -> Unit,
) {
    composable<SelectedPictureRoute> { backStackEntry ->
        val selectedPictureRoute: SelectedPictureRoute = backStackEntry.toRoute<SelectedPictureRoute>()
        SelectedPictureRootScreen(
            pictureKey = selectedPictureRoute.pictureKey,
            navigateToBottomSheetPictureInfoDestination = { pictureKey ->
                val navOptions: NavOptions = navOptions {
                    popUpTo<SelectedPictureRoute> {
                        saveState = true
                        inclusive = false
                    }
                    launchSingleTop = false
                    restoreState = true
                }
                navigateToBottomSheetPictureInfoDestination.invoke(pictureKey, navOptions)
            }
        )
    }
}