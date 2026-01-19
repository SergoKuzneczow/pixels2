package com.sergokuzneczow.settings.impl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.settings.impl.SettingsScreenState

@Composable
internal fun SettingsScreen(
    state: SettingsScreenState,
    changeThemeState: (newThemeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
) {
    when (state) {
        is SettingsScreenState.Loading -> PixelsProgressIndicator(Dimensions.SmallProgressBarSize)

        is SettingsScreenState.Success -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(Dimensions.PixelsTopBarBoxHeight))
                /**
                 * Application settings block */
                ThemeStatesChoice(
                    startValue = state.themeState,
                    onSelectChoice = { themeState -> changeThemeState.invoke(themeState) },
                    isChanging = state.changingThemeState,
                )
            }
        }
    }
}