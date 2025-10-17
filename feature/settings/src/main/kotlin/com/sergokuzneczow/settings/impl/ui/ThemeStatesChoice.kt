package com.sergokuzneczow.settings.impl.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.choice_segments.PixelsSingleChoiceSegmentedButtonRow
import com.sergokuzneczow.core.system_components.choice_segments.SingleChoice
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.models.ApplicationSettings

@Composable
internal fun ThemeStatesChoice(
    startValue: ApplicationSettings.SystemSettings.ThemeState,
    onSelectChoice: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    enable: Boolean = true,
) {
    val options: List<SingleChoice<ApplicationSettings.SystemSettings.ThemeState>> = listOf(
        SingleChoice(
            label = "Light",
            value = ApplicationSettings.SystemSettings.ThemeState.LIGHT,
        ),
        SingleChoice(
            label = "Dark",
            value = ApplicationSettings.SystemSettings.ThemeState.DARK,
        ),
        SingleChoice(
            label = "System",
            value = ApplicationSettings.SystemSettings.ThemeState.SYSTEM,
        ),
    )
    val startSelector = options.indexOfFirst { it.value == startValue }
    Text(
        text = "Application theme",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding)
    )
    PixelsSingleChoiceSegmentedButtonRow(
        options = options,
        onItemSelect = { index, value -> onSelectChoice.invoke(value) },
        modifier = Modifier.padding(top = Dimensions.Padding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
        enabled = enable,
        startSelector = if (startSelector == -1) 0 else startSelector,
        hasIcon = false,
    )
    Spacer(modifier = Modifier.height(Dimensions.LargePadding))
}

@ThemePreviews
@Composable
private fun ThemeStatesChoicePreview(){
    PixelsTheme {
        ThemeStatesChoice(
            startValue = ApplicationSettings.SystemSettings.ThemeState.LIGHT,
            onSelectChoice = {},
        )
    }
}