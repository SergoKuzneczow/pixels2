package com.sergokuzneczow.application_setup.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenState
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.models.ApplicationSettings

@Composable
internal fun ApplicationSetupScreen(
    uiState: ApplicationSetupScreenState,
    onChangeThemeState: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    onDone: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            ApplicationSetupScreenState.Loading -> PixelsProgressIndicator()

            is ApplicationSetupScreenState.ShowSelectedTheme -> {
                ThemeSelector(
                    themeState = uiState.themeState,
                    onThemeItemClick = onChangeThemeState,
                    onDoneClick = onDone,
                )
            }
        }
    }
}