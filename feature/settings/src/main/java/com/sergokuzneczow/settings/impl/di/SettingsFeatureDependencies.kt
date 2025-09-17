package com.sergokuzneczow.settings.impl.di

public interface SettingsFeatureDependencies {

    public interface Contract {
        public fun settingsFeatureDependenciesProvide(): SettingsFeatureDependencies
    }
}