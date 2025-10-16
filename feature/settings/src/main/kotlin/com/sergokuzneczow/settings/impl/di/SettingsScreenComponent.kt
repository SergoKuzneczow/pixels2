package com.sergokuzneczow.settings.impl.di

import com.sergokuzneczow.settings.impl.SettingsScreenViewModel
import dagger.Component

@Component(
    dependencies = [SettingsFeatureDependencies::class]
)
internal interface SettingsScreenComponent {
    fun inject(destination: SettingsScreenViewModel)
    @Component.Builder
    interface Builder {
        fun setDep(d: SettingsFeatureDependencies): Builder
        fun build(): SettingsScreenComponent
    }
}