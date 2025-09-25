package com.sergokuzneczow.pixels2.di

import android.content.Context
import androidx.annotation.NonUiContext
import com.sergokuzneczow.dialog_page_filter.impl.di.DialogPageFilterDependencies
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_first_page_key.GetFirstPageKey
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPagerUseCase
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelationsCase
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPagerUseCase
import com.sergokuzneczow.home.impl.di.HomeFeatureDependencies
import com.sergokuzneczow.pixels2.MainActivityViewModel
import com.sergokuzneczow.pixels2.PixelsApplication
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.search_suitable_pictures.impl.di.SearchSuitablePicturesDependencies
import com.sergokuzneczow.selected_picture.impl.di.SelectedPictureFeatureDependencies
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
    SearchSuitablePicturesDependencies,
    SelectedPictureFeatureDependencies {

    override val getHomeScreenPagerUseCase: GetHomeScreenPagerUseCase

    override val imageLoaderApi: ImageLoaderApi

    override val getSuitablePicturesScreenPagerUseCase: GetSuitablePicturesScreenPagerUseCase

    override val getPictureWithRelationsCase: GetPictureWithRelationsCase

    override val getFirstPageKey: GetFirstPageKey

    override val getPage: GetPage

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