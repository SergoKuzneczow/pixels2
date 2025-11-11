package com.sergokuzneczow.splash.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sergokuzneczow.splash.api.SplashScreenRoute
import com.sergokuzneczow.splash.impl.SplashScreenUiState

@Composable
internal fun SplashScreen(
    uiState: SplashScreenUiState,
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
    navigateToApplicationSetup: (NavOptions?) -> Unit,
) {
    val navOptions: NavOptions = navOptions {
        popUpTo<SplashScreenRoute> {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            SplashScreenUiState.Loading -> onChangeProgressBar.invoke(true)

            is SplashScreenUiState.Success -> {
                if (uiState.hasSettings) navigateToMainMenu.invoke(navOptions)
                else navigateToApplicationSetup.invoke(navOptions)
            }
        }
    }
}