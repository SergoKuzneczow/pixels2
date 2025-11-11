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
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    onChangeThemeState: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    onDone: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            ApplicationSetupScreenUiState.Loading -> onChangeProgressBar.invoke(true)

            is ApplicationSetupScreenUiState.SelectingTheme -> {
                onChangeProgressBar.invoke(false)
                SelectThemeState(
                    themeState = uiState.themeState,
                    onThemeItemClick = onChangeThemeState,
                    onDoneClick = onDone,
                )
            }
        }
    }
}