package com.sergokuzneczow.settings.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.settings.impl.SettingsScreenUiState

@Composable
internal fun SettingsScreen(
    uiState: SettingsScreenUiState,
    changeThemeState: (themeState: ApplicationSettings) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is SettingsScreenUiState.Loading -> PixelsProgressIndicator(Dimensions.SmallProgressBarSize)

            is SettingsScreenUiState.Success -> {
                Spacer(modifier = Modifier.height(Dimensions.PixelsTopBarBoxHeight))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.LargePadding)
                        .clip(Dimensions.PixelsShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    ThemeStatesChoice(
                        startValue = uiState.settings.systemSettings.themeState,
                        onSelectChoice = { themeState ->
                            changeThemeState.invoke(uiState.settings.copy(systemSettings = uiState.settings.systemSettings.copy(themeState = themeState)))
                        },
                    )
                }
            }
        }
    }
}