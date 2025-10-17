package com.sergokuzneczow.settings.impl.di

import com.sergokuzneczow.settings.impl.view_model.SettingsScreenViewModelFactory
import dagger.Component

@Component(
    dependencies = [SettingsFeatureDependencies::class]
)
internal interface SettingsFeatureComponent {

    fun inject(destination: SettingsScreenViewModelFactory)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: SettingsFeatureDependencies): Builder
        fun build(): SettingsFeatureComponent
    }

    object Instance {

        private var component: SettingsFeatureComponent? = null

        internal fun get(d: SettingsFeatureDependencies): SettingsFeatureComponent {
            if (component == null) {
                component = DaggerSettingsFeatureComponent.builder()
                    .setDependencies(d)
                    .build()
            }
            return component ?: throw IllegalStateException("DaggerBottomSheetPictureInformationFeatureComponent must be initialize.")
        }

        internal fun clear() {
            component = null
        }
    }
}