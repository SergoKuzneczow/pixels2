package com.sergokuzneczow.suitable_pictures.api

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data class SuitablePicturesRoute(public val pageKey: Long)

public fun NavHostController.navigateToSuitablePicturesRoute(pageKey: Long, navOptions: NavOptions? = null) {
    this.navigate(route = SuitablePicturesRoute(pageKey), navOptions = navOptions)
}

public fun NavGraphBuilder.composableSuitablePicturesRoute(
    titleState: MutableState<String>,
    showProgressBar: (visible: Boolean) -> Unit,
) {
    composable<SuitablePicturesRoute> { backStackEntry ->
        val data: SuitablePicturesRoute = backStackEntry.toRoute()
        SuitablePicturesRootScreen(
            pageKey = data.pageKey,
            titleState = titleState,
            showProgressBar = showProgressBar,
        )
    }
}