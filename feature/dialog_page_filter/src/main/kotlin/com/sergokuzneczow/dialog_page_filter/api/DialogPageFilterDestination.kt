package com.sergokuzneczow.dialog_page_filter.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.sergokuzneczow.dialog_page_filter.impl.DialogPageFilterRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data class DialogPageFilterRoute(public val pageKey: Long)

public fun NavHostController.navigateToDialogPageFilterDestination(pageKey: Long, navOptions: NavOptions? = null) {
    this.navigate(route = DialogPageFilterRoute(pageKey), navOptions = navOptions)
}

public fun NavGraphBuilder.dialogPageFilterScreenDestination(
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    dialog<DialogPageFilterRoute> { backStackEntry ->
        val data: DialogPageFilterRoute = backStackEntry.toRoute()
        DialogPageFilterRootScreen(
            pageKey = data.pageKey,
            navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        )
    }
}