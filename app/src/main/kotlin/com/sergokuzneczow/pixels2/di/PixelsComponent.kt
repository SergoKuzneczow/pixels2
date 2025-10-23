package com.sergokuzneczow.pixels2.di

import android.content.Context
import androidx.annotation.NonUiContext
import com.sergokuzneczow.bottom_sheet_page_filter.impl.di.BottomSheetPageFilterDependencies
import com.sergokuzneczow.bottom_sheet_picture_info.impl.di.BottomSheetPictureInformationFeatureDependencies
import com.sergokuzneczow.dialog_page_filter.impl.di.DialogPageFilterDependencies
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_home_screen_pager_use_case.GetHomeScreenPager4UseCase
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPager4UseCase
import com.sergokuzneczow.home.impl.di.HomeFeatureDependencies
import com.sergokuzneczow.pixels2.PixelsApplication
import com.sergokuzneczow.pixels2.SavePictureServiceProvider
import com.sergokuzneczow.pixels2.view_model.MainActivityViewModelFactory
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import com.sergokuzneczow.search_suitable_pictures.impl.di.SearchSuitablePicturesDependencies
import com.sergokuzneczow.selected_picture.impl.di.SelectedPictureFeatureDependencies
import com.sergokuzneczow.settings.impl.di.SettingsFeatureDependencies
import com.sergokuzneczow.splash.impl.di.SplashFeatureDependencies
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
    BottomSheetPageFilterDependencies,
    SearchSuitablePicturesDependencies,
    SelectedPictureFeatureDependencies,
    BottomSheetPictureInformationFeatureDependencies,
    SplashFeatureDependencies {
    override val imageLoaderApi: ImageLoaderApi
    override val storageRepositoryApi: StorageRepositoryApi
    override val getSuitablePicturesScreenPager4UseCase: GetSuitablePicturesScreenPager4UseCase
    override val getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase
    override val getFirstPageKeyUseCase: GetFirstPageKeyUseCase
    override val getPage: GetPage
    override val pageRepositoryApi: PageRepositoryApi
    override val getPictureWithRelations2UseCase: GetPictureWithRelations2UseCase
    override val settingsRepositoryApi: SettingsRepositoryApi

    fun inject(destination: MainActivityViewModelFactory)

    fun inject(destination: SavePictureServiceProvider.ExampleService)

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