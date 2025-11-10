package com.sergokuzneczow.application_setup.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenUiState
import com.sergokuzneczow.models.ApplicationSettings

@Composable
internal fun ApplicationSetupScreen(
    uiState: ApplicationSetupScreenUiState,
    onChangeThemeStateClick: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    navigateToMainMenu: () -> Unit,
    onDoneClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            ApplicationSetupScreenUiState.Loading -> onChangeProgressBar.invoke(true)

            ApplicationSetupScreenUiState.ApplicationSetupCompleted -> {
                onChangeProgressBar.invoke(false) // change when global progress bar will be using in all features
                navigateToMainMenu.invoke()
            }

            is ApplicationSetupScreenUiState.SelectingTheme -> {
                onChangeProgressBar.invoke(false)
                SelectThemeState(
                    themeState = uiState.themeState,
                    selectedThemeState = onChangeThemeStateClick,
                    onDoneClick = onDoneClick,
                )
            }
        }
    }
}