package com.sergokuzneczow.main_menu.impl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.sergokuzneczow.main_menu.impl.MainMenuRootScreenState
import com.sergokuzneczow.main_menu.impl.navigation.mainMenuGraph
import com.sergokuzneczow.suitable_pictures.api.navigateToSuitablePicturesRoute

@Composable
internal fun MainMenuNavHost(
    rootScreenState: MainMenuRootScreenState,
    titleState: MutableState<String> ,
    showProgressBar: (visible: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = rootScreenState.navController,
        startDestination = rootScreenState.startDestination,
        modifier = modifier,
    ) {
        this.mainMenuGraph(
            navigateToSuitablePicturesDestination = { pageKey -> rootScreenState.navController.navigateToSuitablePicturesRoute(pageKey)},
            titleState = titleState,
            showProgressBar = showProgressBar,
        )
    }
}