package com.sergokuzneczow.suitable_pictures.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sergokuzneczow.suitable_pictures.SuitablePicturesRoot
import kotlinx.serialization.Serializable

@Serializable
public data object SuitablePicturesScreenDestination

public fun NavHostController.navigateToSuitablePicturesScreenDestination(navOptions: NavOptions? = null) {
    this.navigate(route = SuitablePicturesScreenDestination, navOptions = navOptions)
}

public fun NavGraphBuilder.composableSuitablePicturesScreenDestination() {
    composable<SuitablePicturesScreenDestination> {
        SuitablePicturesRoot()
    }
}