package com.sergokuzneczow.main_menu.impl

import androidx.compose.runtime.Composable
import com.sergokuzneczow.main_menu.impl.ui.MainMenuScreen

@Composable
internal fun MainMenuRootScreen(navigateToSuitablePicturesDestination: (Long) -> Unit) {
    MainMenuScreen(navigateToSuitablePicturesDestination)
}