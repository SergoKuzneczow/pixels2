package com.sergokuzneczow.splash.impl

import com.sergokuzneczow.base.BaseAction
import com.sergokuzneczow.base.BaseIntent
import com.sergokuzneczow.base.BaseState
import com.sergokuzneczow.models.ApplicationSettings

internal sealed interface SplashScreenState : BaseState {
    data object CheckingFirstLaunch : SplashScreenState
}

internal sealed interface SplashScreenIntent : BaseIntent

internal sealed interface SplashScreenAction : BaseAction {
    data object IsFirstLaunch : SplashScreenAction
    data class IsNotFirstLaunch(val settings: ApplicationSettings) : SplashScreenAction
}