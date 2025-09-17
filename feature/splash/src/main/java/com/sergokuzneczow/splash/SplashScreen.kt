package com.sergokuzneczow.splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.splash.navgiation.SplashScreenRoute

@Composable
internal fun SplashScreen(
    navigateToMainMenu: (NavOptions?) -> Unit,
) {
    val navOptions = navOptions {
        popUpTo<SplashScreenRoute> {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }

    navigateToMainMenu.invoke(navOptions)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = {  })
    ) {
        PixelsCircularProgressIndicator()
    }
}