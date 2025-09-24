package com.sergokuzneczow.pixels2.di

import android.content.Context
import androidx.annotation.NonUiContext
import com.sergokuzneczow.dialog_page_filter.impl.di.DialogPageFilterDependencies
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPagerUseCase
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPagerUseCase
import com.sergokuzneczow.home.impl.di.HomeFeatureDependencies
import com.sergokuzneczow.pixels2.MainActivityViewModel
import com.sergokuzneczow.pixels2.PixelsApplication
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.search_suitable_pictures.impl.di.SearchSuitablePicturesDependencies
import com.sergokuzneczow.settings.impl.di.SettingsFeatureDependencies
import com.sergokuzneczow.suitable_pictures.impl.di.SuitablePicturesFeatureDependencies
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
    HomeFeatureDependencies,
    SettingsFeatureDependencies,
    SuitablePicturesFeatureDependencies,
    DialogPageFilterDependencies,
    SearchSuitablePicturesDependencies {

    override val getHomeScreenPagerUseCase: GetHomeScreenPagerUseCase

    override val imageLoaderApi: ImageLoaderApi

    override val pageRepositoryApi: PageRepositoryApi

    override val getSuitablePicturesScreenPagerUseCase: GetSuitablePicturesScreenPagerUseCase

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