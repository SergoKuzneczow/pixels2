package com.sergokuzneczow.models

public data class ApplicationSettings(
    val userSettings: UserSettings,
    val systemSettings: SystemSettings,
) {

    public companion object {
        public val DEFAULT: ApplicationSettings = ApplicationSettings(
            userSettings = UserSettings.DEFAULT,
            systemSettings = SystemSettings.DEFAULT,
        )
    }

    public data class UserSettings(
        val purityMode: Boolean,
    ) {

        public companion object {
            public val DEFAULT: UserSettings = UserSettings(
                purityMode = false,
            )
        }
    }

    public data class SystemSettings(
        val themeState: ThemeState,
    ) {

        public companion object {
            public val DEFAULT: SystemSettings = SystemSettings(
                themeState = ThemeState.SYSTEM,
            )
        }

        public enum class ThemeState {
            LIGHT,
            DARK,
            SYSTEM,
        }
    }
}
