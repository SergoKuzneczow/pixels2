package com.sergokuzneczow.splash.impl

import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.utilities.BaseAction
import com.sergokuzneczow.utilities.BaseIntent
import com.sergokuzneczow.utilities.BaseState

internal sealed interface SplashScreenState  {
    data object CheckingFirstLaunch : SplashScreenState
}

internal sealed interface SplashScreenIntent {
    data object CheckLaunchStatus : SplashScreenIntent
}

internal sealed interface SplashScreenAction {
    data object IsFirstLaunch : SplashScreenAction
    data class IsNotFirstLaunch(val settings: ApplicationSettings) : SplashScreenAction
}