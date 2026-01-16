package com.sergokuzneczow.splash.impl

import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.utilities.BaseAction
import com.sergokuzneczow.utilities.BaseIntent
import com.sergokuzneczow.utilities.BaseState

internal sealed interface SplashScreenState : BaseState {
    data object CheckingFirstLaunch : SplashScreenState
}

internal sealed interface SplashScreenIntent : BaseIntent

internal sealed interface SplashScreenAction : BaseAction {
    data object IsFirstLaunch : SplashScreenAction
    data class IsNotFirstLaunch(val settings: ApplicationSettings) : SplashScreenAction
}