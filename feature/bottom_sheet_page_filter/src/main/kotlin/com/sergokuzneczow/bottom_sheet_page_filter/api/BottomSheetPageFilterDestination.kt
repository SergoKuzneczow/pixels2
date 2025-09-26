package com.sergokuzneczow.bottom_sheet_page_filter.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.sergokuzneczow.bottom_sheet_page_filter.impl.BottomSheetPageFilterRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data class BottomSheetPageFilterRoute(public val pageKey: Long)

public fun NavHostController.navigateToBottomSheetPageFilterDestination(pageKey: Long, navOptions: NavOptions? = null) {
    this.navigate(route = BottomSheetPageFilterRoute(pageKey), navOptions = navOptions)
}

public fun NavGraphBuilder.bottomSheetPageFilterScreenDestination(
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
    popBackStack: () -> Unit,
) {
    dialog<BottomSheetPageFilterRoute> { backStackEntry ->
        val data: BottomSheetPageFilterRoute = backStackEntry.toRoute()
        BottomSheetPageFilterRootScreen(
            pageKey = data.pageKey,
            navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
            popBackStack = popBackStack,
        )
    }
}