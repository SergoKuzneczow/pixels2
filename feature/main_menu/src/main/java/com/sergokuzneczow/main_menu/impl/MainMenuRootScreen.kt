package com.sergokuzneczow.main_menu.impl

import androidx.compose.runtime.Composable
import com.sergokuzneczow.main_menu.impl.ui.MainMenuScreen

@Composable
internal fun MainMenuRootScreen(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    navigateToSuitablePicturesDestination: (Long) -> Unit
) {
    MainMenuScreen(
        onShowSnackbar = onShowSnackbar,
        navigateToSuitablePicturesDestination
    )
}