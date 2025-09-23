package com.sergokuzneczow.splash.impl

import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions


@Composable
internal fun SplashRootScreen(
    navigateToMainMenu: (NavOptions?) -> Unit,
) {
    SplashScreen(navigateToMainMenu = navigateToMainMenu)
}