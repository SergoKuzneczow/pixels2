package com.sergokuzneczow.splash.impl.di

import com.sergokuzneczow.splash.impl.view_model.SplashScreenViewModelFactory
import dagger.Component

@Component(
    dependencies = [SplashFeatureDependencies::class]
)
internal interface SplashFeatureComponent {

    fun inject(destination: SplashScreenViewModelFactory)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: SplashFeatureDependencies): Builder
        fun build(): SplashFeatureComponent
    }

    object Instance {

        private var component: SplashFeatureComponent? = null

        internal fun get(d: SplashFeatureDependencies): SplashFeatureComponent {
            if (component == null) {
                component = DaggerSplashFeatureComponent.builder()
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