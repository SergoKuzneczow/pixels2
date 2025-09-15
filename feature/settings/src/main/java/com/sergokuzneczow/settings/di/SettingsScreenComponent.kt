package com.sergokuzneczow.settings.di

import com.sergokuzneczow.settings.SettingsScreenViewModel
import dagger.Component

@Component(
    dependencies = [SettingsScreenDependencies::class]
)
internal interface SettingsScreenComponent {
    fun inject(destination: SettingsScreenViewModel)
    @Component.Builder
    interface Builder {
        fun setDep(d: SettingsScreenDependencies): Builder
        fun build(): SettingsScreenComponent
    }
}