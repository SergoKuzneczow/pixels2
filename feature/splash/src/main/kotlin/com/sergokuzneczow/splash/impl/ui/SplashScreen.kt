package com.sergokuzneczow.splash.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.splash.impl.SplashScreenState

@Composable
internal fun SplashScreen(
    state: SplashScreenState,
) {
    when (state) {
        SplashScreenState.CheckingFirstLaunch -> {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}