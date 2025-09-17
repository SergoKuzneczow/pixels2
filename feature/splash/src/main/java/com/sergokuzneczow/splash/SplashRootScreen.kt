package com.sergokuzneczow.splash

import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions


@Composable
internal fun SplashRootScreen(
    navigateToMainMenu: (NavOptions?) -> Unit,
) {
    SplashScreen(navigateToMainMenu = navigateToMainMenu)
}