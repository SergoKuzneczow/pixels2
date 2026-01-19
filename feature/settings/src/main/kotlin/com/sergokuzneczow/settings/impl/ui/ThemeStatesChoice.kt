package com.sergokuzneczow.settings.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.sergokuzneczow.core.system_components.choice_segments.PixelsSingleChoiceSegmentedButtonRow
import com.sergokuzneczow.core.system_components.choice_segments.SingleChoice
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.models.ApplicationSettings

@Composable
internal fun ThemeStatesChoice(
    startValue: ApplicationSettings.SystemSettings.ThemeState,
    onSelectChoice: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    isChanging: Boolean = true,
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.LargePadding)
            .clip(Dimensions.PixelsShape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Spacer(modifier = Modifier.height(Dimensions.LargePadding))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.LargePadding)
        ) {
            Text(
                text = "Application theme",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterStart),
            )
            if (isChanging) {
                PixelsProgressIndicator(
                    elementSize = Dimensions.SmallProgressBarSize,
                    indicatorAlign = Alignment.CenterEnd,
                    indicatorPaddings = PaddingValues(horizontal = Dimensions.Padding)
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimensions.Padding))
        PixelsSingleChoiceSegmentedButtonRow(
            options = options,
            onItemSelect = { _, value -> onSelectChoice.invoke(value) },
            modifier = Modifier.padding(horizontal = Dimensions.LargePadding),
            enabled = !isChanging,
            startSelector = if (startSelector == -1) 0 else startSelector,
            hasIcon = false,
        )
        Spacer(modifier = Modifier.height(Dimensions.LargePadding))
    }
}

@ThemePreviews
@Composable
private fun ThemeStatesChoicePreview() {
    PixelsTheme {
        Surface {
            ThemeStatesChoice(
                startValue = ApplicationSettings.SystemSettings.ThemeState.LIGHT,
                onSelectChoice = {},
                isChanging = false,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ThemeStatesChoiceChangingPreview() {
    PixelsTheme {
        Surface {
            ThemeStatesChoice(
                startValue = ApplicationSettings.SystemSettings.ThemeState.LIGHT,
                onSelectChoice = {},
                isChanging = true,
            )
        }
    }
}