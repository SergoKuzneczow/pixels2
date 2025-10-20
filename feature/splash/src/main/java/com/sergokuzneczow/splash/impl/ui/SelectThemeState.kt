package com.sergokuzneczow.splash.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.system_components.buttons.PixelsPrimaryButton
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.ui.pixelsClip
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.models.ApplicationSettings


@Composable
internal fun BoxScope.SelectThemeState(
    themeState: ApplicationSettings.SystemSettings.ThemeState,
    selectedThemeState: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .align(Alignment.Center)
            .padding(horizontal = Dimensions.LargePadding)
    ) {
        Text(
            text = "Select theme style",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(Dimensions.LargePadding)
        )
        ThemeStatesChoice(
            startValue = themeState,
            selectedValue = selectedThemeState,
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            PixelsPrimaryButton(
                text = "Done",
                onClick = onDoneClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Dimensions.Padding),
            )
        }
    }
}

@Composable
private fun ThemeStatesChoice(
    startValue: ApplicationSettings.SystemSettings.ThemeState,
    selectedValue: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
) {
    val options: List<ApplicationSettings.SystemSettings.ThemeState> = ApplicationSettings.SystemSettings.ThemeState.entries.toList()
    val startSelector: Int = options.indexOfFirst { it == startValue }
    var selector: Int by remember { mutableIntStateOf(if (startSelector == -1) 0 else startSelector) }
    Row(modifier = Modifier.widthIn(min = 480.dp)) {
        options.forEachIndexed { index, state ->
            ThemeItem(
                isSelected = index == selector,
                title = when (state) {
                    ApplicationSettings.SystemSettings.ThemeState.LIGHT -> "Light"
                    ApplicationSettings.SystemSettings.ThemeState.DARK -> "Dark"
                    ApplicationSettings.SystemSettings.ThemeState.SYSTEM -> "System"
                },
                value = state,
                onSelect = {
                    selector = index
                    selectedValue.invoke(it)
                },
                backgroundBrush = when (state) {
                    ApplicationSettings.SystemSettings.ThemeState.LIGHT -> Brush.verticalGradient(colors = listOf(lightTheme, lightTheme))
                    ApplicationSettings.SystemSettings.ThemeState.DARK -> Brush.verticalGradient(colors = listOf(darkTheme, darkTheme))
                    ApplicationSettings.SystemSettings.ThemeState.SYSTEM -> Brush.verticalGradient(colors = listOf(darkTheme, lightTheme))
                },
                titleColor = when (state) {
                    ApplicationSettings.SystemSettings.ThemeState.LIGHT -> darkTheme
                    ApplicationSettings.SystemSettings.ThemeState.DARK -> lightTheme
                    ApplicationSettings.SystemSettings.ThemeState.SYSTEM -> lightTheme
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Dimensions.SmallPadding)
            )
        }
    }
}

@Composable
private fun <T> ThemeItem(
    isSelected: Boolean,
    title: String,
    value: T,
    onSelect: (value: T) -> Unit,
    backgroundBrush: Brush = Brush.verticalGradient(colors = listOf(darkTheme, lightTheme)),
    titleColor: Color = Color(255, 255, 255, 255),
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .pixelsClip()
            .height(192.dp)
            .background(backgroundBrush)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                shape = Dimensions.PixelsShape,
            )
            .semantics { role = Role.Button }
            .clickable(onClick = { onSelect.invoke(value) })
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = titleColor,
            modifier = Modifier
                .padding(Dimensions.LargePadding)
                .align(Alignment.Center)
        )
    }
}

private val darkTheme: Color = Color(61, 61, 61, 255)

private val lightTheme: Color = Color(239, 239, 239, 255)

@ThemePreviews
@Composable
private fun ThemeStatesChoicePreview() {
    PixelsTheme {
        ThemeStatesChoice(
            startValue = ApplicationSettings.SystemSettings.ThemeState.LIGHT,
            selectedValue = {},
        )
    }
}

@ThemePreviews
@Composable
private fun ThemeSelectorSelectedPreview() {
    PixelsTheme {
        ThemeItem(
            isSelected = true,
            title = "Preview",
            value = 0,
            onSelect = {},
        )
    }
}

@ThemePreviews
@Composable
private fun ThemeSelectorUnselectedPreview() {
    PixelsTheme {
        ThemeItem(
            isSelected = false,
            title = "Preview",
            value = 0,
            onSelect = {},
        )
    }
}