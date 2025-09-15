package com.sergokuzneczow.home.di

import com.sergokuzneczow.home.HomeScreenViewModel
import dagger.Component

@Component(
    dependencies = [HomeScreenDependencies::class]
)
internal interface HomeScreenComponent {
    fun inject(destination: HomeScreenViewModel)

    @Component.Builder
    interface Builder {
        fun setDep(d: HomeScreenDependencies): Builder
        fun build(): HomeScreenComponent
    }
}