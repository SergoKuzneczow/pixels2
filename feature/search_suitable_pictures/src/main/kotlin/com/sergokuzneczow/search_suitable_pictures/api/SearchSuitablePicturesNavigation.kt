package com.sergokuzneczow.search_suitable_pictures.api

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sergokuzneczow.search_suitable_pictures.impl.SearchSuitablePicturesRootScreen
import kotlinx.serialization.Serializable

@Serializable
public data object SearchSuitablePicturesRoute

public fun NavHostController.navigateToSearchSuitablePicturesDestination(navOptions: NavOptions? = null) {
    this.navigate(route = SearchSuitablePicturesRoute, navOptions = navOptions)
}

public fun NavGraphBuilder.composableSearchSuitablePicturesDestination(
    titleTextState: MutableState<String>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    composable<SearchSuitablePicturesRoute> {
        SearchSuitablePicturesRootScreen(
            titleTextState = titleTextState,
            navigateToSuitablePicturesDestination = navigateToSuitablePicturesDestination,
        )
    }
}