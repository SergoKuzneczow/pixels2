package com.sergokuzneczow.splash.impl.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.splash.api.SplashScreenRoute
import com.sergokuzneczow.splash.impl.SplashScreenUiState

@Composable
internal fun SplashScreen(
    uiState: SplashScreenUiState,
    onChangeThemeStateClick: (themeState: ApplicationSettings.SystemSettings.ThemeState) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
    onDoneClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {

            SplashScreenUiState.Loading -> {
                PixelsProgressIndicator()
            }

            SplashScreenUiState.Finish -> {
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