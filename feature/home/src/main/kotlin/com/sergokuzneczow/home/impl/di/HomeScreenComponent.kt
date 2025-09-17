package com.sergokuzneczow.home.impl.di

import com.sergokuzneczow.home.impl.HomeScreenViewModel
import dagger.Component

@Component(
    dependencies = [HomeFeatureDependencies::class]
)
internal interface HomeScreenComponent {
    fun inject(destination: HomeScreenViewModel)

    @Component.Builder
    interface Builder {
        fun setDep(d: HomeFeatureDependencies): Builder
        fun build(): HomeScreenComponent
    }
}