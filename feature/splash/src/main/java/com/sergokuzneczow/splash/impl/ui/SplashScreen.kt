package com.sergokuzneczow.splash.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.system_components.buttons.PixelsPrimaryButton
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.ui.pixelsClip
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.models.PixelsSettings
import com.sergokuzneczow.splash.api.SplashScreenRoute

@Composable
internal fun SplashScreen(
    uiState: SplashScreenUiState,
    onChangeThemeStateClick: (themeState: PixelsSettings.ThemeState) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
    onDoneClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {

            SplashScreenUiState.Loading -> {
                PixelsCircularProgressIndicator()
            }

            SplashScreenUiState.Success -> {
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

@Composable
internal fun BoxScope.SelectThemeState(
    themeState: PixelsSettings.ThemeState,
    selectedThemeState: (themeState: PixelsSettings.ThemeState) -> Unit,
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
    startValue: PixelsSettings.ThemeState,
    selectedValue: (themeState: PixelsSettings.ThemeState) -> Unit,
) {
    val options: List<PixelsSettings.ThemeState> = PixelsSettings.ThemeState.entries.toList()
    val startSelector: Int = options.indexOfFirst { it == startValue }
    var selector: Int by remember { mutableIntStateOf(if (startSelector == -1) 0 else startSelector) }
    Row(modifier = Modifier.widthIn(min = 480.dp)) {
        options.forEachIndexed { index, state ->
            ThemeSelector(
                isSelected = index == selector,
                title = state.name,
                backgroundBrush = when (state) {
                    PixelsSettings.ThemeState.LIGHT -> Brush.verticalGradient(colors = listOf(white, white))
                    PixelsSettings.ThemeState.DARK -> Brush.verticalGradient(colors = listOf(black, black))
                    PixelsSettings.ThemeState.SYSTEM -> Brush.verticalGradient(colors = listOf(black, white))
                },
                titleColor = when (state) {
                    PixelsSettings.ThemeState.LIGHT -> black
                    PixelsSettings.ThemeState.DARK -> white
                    PixelsSettings.ThemeState.SYSTEM -> white
                },
                value = state,
                onSelect = {
                    selector = index
                    selectedValue.invoke(it)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Dimensions.SmallPadding)
            )
        }
    }
}

@Composable
private fun <T> ThemeSelector(
    isSelected: Boolean,
    title: String,
    backgroundBrush: Brush = Brush.verticalGradient(colors = listOf(black, white)),
    titleColor: Color = Color(255, 255, 255, 255),
    value: T,
    onSelect: (value: T) -> Unit,
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

private val black: Color = Color(71, 71, 71, 255)

private val white: Color = Color(239, 239, 239, 255)

@ThemePreviews
@Composable
private fun ThemeStatesChoicePreview() {
    PixelsTheme {
        ThemeStatesChoice(
            startValue = PixelsSettings.ThemeState.LIGHT,
            selectedValue = {},
        )
    }
}

@ThemePreviews
@Composable
private fun ThemeSelectorSelectedPreview() {
    PixelsTheme {
        ThemeSelector(
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
        ThemeSelector(
            isSelected = false,
            title = "Preview",
            value = 0,
            onSelect = {},
        )
    }
}