package com.sergokuzneczow.home.impl.di

import com.sergokuzneczow.home.impl.view_model.HomeScreenViewModel
import com.sergokuzneczow.home.impl.view_model.HomeScreenViewModelFactory
import dagger.Component

@Component(
    dependencies = [HomeFeatureDependencies::class]
)
internal interface HomeFeatureComponent {
    fun inject(destination: HomeScreenViewModelFactory)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: HomeFeatureDependencies): Builder
        fun build(): HomeFeatureComponent
    }

    object Instance {

        private var component: HomeFeatureComponent? = null

        internal fun get(d: HomeFeatureDependencies): HomeFeatureComponent {
            if (component == null) {
                component = DaggerHomeFeatureComponent.builder()
                    .setDependencies(d)
                    .build()
            }
            return component ?: throw IllegalStateException("DaggerHomeScreenComponent must be initialize.")
        }

        internal fun clear() {
            component = null
        }
    }
}