package com.sergokuzneczow.application_setup.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.system_components.buttons.PixelsPrimaryButton
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.ui.pixelsContainerClip
import com.sergokuzneczow.core.ui.pixelsContainerShadow
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.core.utilites.ThemeUiPreviews
import com.sergokuzneczow.models.ApplicationSettings

private val THEME_PREVIEW_HEIGHT: Dp = 192.dp

@Composable
internal fun BoxScope.SelectThemeState(
    themeState: ApplicationSettings.SystemSettings.ThemeState,
    onThemeItemClick: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = Dimensions.LargePadding)
            .clip(Dimensions.ContainerCornerShape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .align(Alignment.Center)
    ) {
        Spacer(modifier = Modifier.height(Dimensions.LargePadding))
        Text(
            text = "Select theme style",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = Dimensions.LargePadding)
        )
        Spacer(modifier = Modifier.height(Dimensions.LargePadding))
        ThemeStatesChoice(
            startValue = themeState,
            onSelectValue = onThemeItemClick,
        )
        Spacer(modifier = Modifier.height(Dimensions.LargePadding))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.LargePadding)
        ) {
            PixelsPrimaryButton(
                text = "Done",
                onClick = onDoneClick,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
        Spacer(modifier = Modifier.height(Dimensions.LargePadding))
    }
}

@Composable
private fun ThemeStatesChoice(
    startValue: ApplicationSettings.SystemSettings.ThemeState,
    onSelectValue: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
) {
    val options: List<ApplicationSettings.SystemSettings.ThemeState> = ApplicationSettings.SystemSettings.ThemeState.entries.toList()
    val startSelector: Int = options.indexOfFirst { it == startValue }
    var selectedPosition: Int by remember { mutableIntStateOf(if (startSelector == -1) 0 else startSelector) }
    Row(
        modifier = Modifier
            .widthIn(min = 480.dp)
            .padding(horizontal = Dimensions.LargePadding)
    ) {
        options.forEachIndexed { index, state ->
            ThemeItem(
                isSelected = index == selectedPosition,
                title = when (state) {
                    ApplicationSettings.SystemSettings.ThemeState.LIGHT -> "Light"
                    ApplicationSettings.SystemSettings.ThemeState.DARK -> "Dark"
                    ApplicationSettings.SystemSettings.ThemeState.SYSTEM -> "System"
                },
                value = state,
                onItemClick = {
                    selectedPosition = index
                    onSelectValue.invoke(it)
                },
                backgroundBrush = when (state) {
                    ApplicationSettings.SystemSettings.ThemeState.LIGHT -> ThemeItemColors.lightTheme
                    ApplicationSettings.SystemSettings.ThemeState.DARK -> ThemeItemColors.darkTheme
                    ApplicationSettings.SystemSettings.ThemeState.SYSTEM -> ThemeItemColors.systemTheme
                },
                titleColor = when (state) {
                    ApplicationSettings.SystemSettings.ThemeState.LIGHT -> ThemeItemColors.onLight
                    ApplicationSettings.SystemSettings.ThemeState.DARK -> ThemeItemColors.onDark
                    ApplicationSettings.SystemSettings.ThemeState.SYSTEM -> ThemeItemColors.onDark
                },
                modifier = Modifier.weight(1f)
            )
            if (index != options.size - 1) Spacer(modifier = Modifier.width(Dimensions.Padding))
        }
    }
}

@Composable
private fun <T> ThemeItem(
    isSelected: Boolean,
    title: String,
    value: T,
    onItemClick: (value: T) -> Unit,
    modifier: Modifier = Modifier,
    backgroundBrush: Brush = ThemeItemColors.lightTheme,
    titleColor: Color = Color(0, 0, 0, 255),
) {
    Box(
        modifier = modifier
            .pixelsContainerShadow()
            .pixelsContainerClip()
            .height(THEME_PREVIEW_HEIGHT)
            .background(backgroundBrush)
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else ThemeItemColors.transparent,
                shape = Dimensions.ContainerCornerShape,
            )
            .semantics { role = Role.Button }
            .clickable(onClick = { onItemClick.invoke(value) })
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = titleColor,
            modifier = Modifier
                .padding(Dimensions.LargePadding)
                .align(Alignment.Center)
        )
    }
}

private object ThemeItemColors {
    val dark: Color = Color(36, 36, 36, 255)
    val light: Color = Color(255, 255, 255, 255)
    val onDark: Color = Color(245, 245, 245, 255)
    val onLight: Color = Color(28, 28, 28, 255)
    val lightTheme: Brush = Brush.verticalGradient(colors = listOf(light, light))
    val darkTheme: Brush = Brush.verticalGradient(colors = listOf(dark, dark))
    val systemTheme: Brush = Brush.verticalGradient(colors = listOf(dark, light))
    val transparent: Color = Color(0, 0, 0, 0)
}

@ThemeUiPreviews
@Composable
private fun SelectThemeStatePreview() {
    PixelsTheme {
        Surface {
            Box(modifier = Modifier.fillMaxWidth()) {
                SelectThemeState(
                    themeState = ApplicationSettings.SystemSettings.ThemeState.SYSTEM,
                    onThemeItemClick = {},
                    onDoneClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun ThemeStatesChoicePreview() {
    PixelsTheme {
        ThemeStatesChoice(
            startValue = ApplicationSettings.SystemSettings.ThemeState.LIGHT,
            onSelectValue = {},
        )
    }
}