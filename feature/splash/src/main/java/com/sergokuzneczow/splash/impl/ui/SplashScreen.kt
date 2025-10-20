package com.sergokuzneczow.splash.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.splash.api.SplashScreenRoute
import com.sergokuzneczow.splash.impl.SplashScreenUiState

@Composable
internal fun SplashScreen(
    uiState: SplashScreenUiState,
    onChangeThemeStateClick: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
    onDoneClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {

            SplashScreenUiState.Loading -> {
                PixelsCircularProgressIndicator()
            }

            SplashScreenUiState.Finish -> {
                val navOptions: NavOptions = navOptions {
                    popUpTo<SplashScreenRoute> {
                        saveState = true
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                navigateToMainMenu.invoke(navOptions)
            }

            is SplashScreenUiState.SelectingThemeState -> {
                SelectThemeState(
                    themeState = uiState.themeState,
                    selectedThemeState = onChangeThemeStateClick,
                    onDoneClick = onDoneClick,
                )
            }
        }
    }
}