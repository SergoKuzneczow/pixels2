package com.sergokuzneczow.suitable_pictures.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data class SuitablePicturesRoute(public val pageKey: Long)

public fun NavHostController.navigateToSuitablePicturesRoute(pageKey: Long, navOptions: NavOptions? = null) {
    this.navigate(route = SuitablePicturesRoute(pageKey), navOptions = navOptions)
}

public fun NavGraphBuilder.suitablePicturesScreenDestination(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    navigateToDialogPageFilterDestination: (pageKey: Long, navOptions: NavOptions?) -> Unit,
    navigateToSelectedPictureDestination: (pictureKey: String) -> Unit,
    backMainMenu: () -> Unit,
) {
    composable<SuitablePicturesRoute> { backStackEntry ->
        val data: SuitablePicturesRoute = backStackEntry.toRoute()
        SuitablePicturesRootScreen(
            pageKey = data.pageKey,
            onShowSnackbar = onShowSnackbar,
            navigateToDialogPageFilterDestination = { pageKey ->
                val navOptions: NavOptions = navOptions {
                    popUpTo<SuitablePicturesRoute> {
                        saveState = true
                        inclusive = false
                    }
                    launchSingleTop = false
                    restoreState = true
                }
                navigateToDialogPageFilterDestination.invoke(pageKey, navOptions)
            },
            navigateToSelectedPictureDestination = navigateToSelectedPictureDestination,
            backMainMenu = backMainMenu,
        )
    }
}