package com.sergokuzneczow.application_setup.impl.di

import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenViewModelFactory
import dagger.Component

@Component(
    dependencies = [ApplicationSetupFeatureDependencies::class]
)
internal interface ApplicationSetupFeatureComponent {

    fun inject(destination: ApplicationSetupScreenViewModelFactory)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: ApplicationSetupFeatureDependencies): Builder
        fun build(): ApplicationSetupFeatureComponent
    }

    object Instance {

        private var component: ApplicationSetupFeatureComponent? = null

        internal fun get(d: ApplicationSetupFeatureDependencies): ApplicationSetupFeatureComponent {
            if (component == null) {
                component = DaggerApplicationSetupFeatureComponent.builder()
                    .setDependencies(d)
                    .build()
            }
            return component ?: throw IllegalStateException("DaggerSplashFeatureComponent must be initialize.")
        }

        internal fun clear() {
            component = null
        }
    }
}