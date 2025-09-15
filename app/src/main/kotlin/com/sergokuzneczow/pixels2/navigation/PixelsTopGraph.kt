package com.sergokuzneczow.pixels2.navigation

import androidx.navigation.NavGraphBuilder
import com.sergokuzneczow.home.navigation.homeScreenDestination
import com.sergokuzneczow.settings.navigation.settingsScreenDestination

internal fun NavGraphBuilder.pixelsTopDestinations(
    changeTitle: (title: String) -> Unit,
    showProgressBar: (visible: Boolean) -> Unit,
) {
    homeScreenDestination(
        changeTitle = changeTitle,
        showProgressBar = showProgressBar
    )
    settingsScreenDestination(
        changeTitle = changeTitle,
        showProgressBar = showProgressBar
    )
}