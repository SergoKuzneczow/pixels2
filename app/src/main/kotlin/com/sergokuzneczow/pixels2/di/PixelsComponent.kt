package com.sergokuzneczow.pixels2.di

import android.content.Context
import androidx.annotation.NonUiContext
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPagerUseCase
import com.sergokuzneczow.home.di.HomeScreenDependencies
import com.sergokuzneczow.pixels2.MainActivityViewModel
import com.sergokuzneczow.pixels2.PixelsApplication
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.settings.di.SettingsScreenDependencies
import dagger.BindsInstance
import dagger.Component
import jakarta.inject.Singleton

@Component(
    modules = [
        CoreBinds::class,
    ]
)
@Singleton
internal interface PixelsComponent :
    HomeScreenDependencies,
    SettingsScreenDependencies {

    override fun getGetHomeScreenPagerUseCase(): GetHomeScreenPagerUseCase

    override fun getImageLoaderApi(): ImageLoaderApi

    fun inject(destination: MainActivityViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setContext(@NonUiContext context: Context): Builder
        fun build(): PixelsComponent
    }
}

internal val Context.applicationComponent: PixelsComponent
    get() = when (this) {
        is PixelsApplication -> this.pixelsComponent
        else -> this.applicationContext.applicationComponent
    }