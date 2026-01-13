package com.sergokuzneczow.application_setup.impl

import com.sergokuzneczow.base.BaseAction
import com.sergokuzneczow.base.BaseIntent
import com.sergokuzneczow.base.BaseState
import com.sergokuzneczow.models.ApplicationSettings

internal sealed interface ApplicationSetupScreenState : BaseState {
    data object Loading : ApplicationSetupScreenState
    data class ShowSelectedTheme(val themeState: ApplicationSettings.SystemSettings.ThemeState) : ApplicationSetupScreenState
}

internal sealed interface ApplicationSetupScreenIntent : BaseIntent {
    data class ChangeTheme(val newThemeState: ApplicationSettings.SystemSettings.ThemeState) : ApplicationSetupScreenIntent
    data object Done : ApplicationSetupScreenIntent
}

internal sealed interface ApplicationSetupScreenAction : BaseAction {
    data object Completed : ApplicationSetupScreenAction
}