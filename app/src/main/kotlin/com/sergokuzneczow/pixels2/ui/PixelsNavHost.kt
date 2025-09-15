package com.sergokuzneczow.pixels2.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.sergokuzneczow.pixels2.navigation.PixelsState
import com.sergokuzneczow.pixels2.navigation.pixelsTopDestinations

@Composable
internal fun PixelsNavHost(
    applicationState: PixelsState,
    changeTitle: (title: String) -> Unit,
    showProgressBar: (visible: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = applicationState.navController,
        startDestination = applicationState.startDestination.baseRoute,
        modifier = modifier,
    ) {
        this.pixelsTopDestinations(
            changeTitle = changeTitle,
            showProgressBar = showProgressBar,
        )
    }
}