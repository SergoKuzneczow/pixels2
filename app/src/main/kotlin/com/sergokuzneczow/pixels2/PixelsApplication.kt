package com.sergokuzneczow.pixels2

import android.app.Application
import com.sergokuzneczow.application_setup.impl.di.ApplicationSetupFeatureDependencies
import com.sergokuzneczow.bottom_sheet_page_filter.impl.di.BottomSheetPageFilterDependencies
import com.sergokuzneczow.bottom_sheet_picture_info.impl.di.BottomSheetPictureInformationFeatureDependencies
import com.sergokuzneczow.dialog_page_filter.impl.di.DialogPageFilterDependencies
import com.sergokuzneczow.home.impl.di.HomeFeatureDependencies
import com.sergokuzneczow.pixels2.di.DaggerPixelsComponent
import com.sergokuzneczow.pixels2.di.PixelsComponent
import com.sergokuzneczow.pixels2.di.applicationComponent
import com.sergokuzneczow.search_suitable_pictures.impl.di.SearchSuitablePicturesDependencies
import com.sergokuzneczow.selected_picture.impl.di.SelectedPictureFeatureDependencies
import com.sergokuzneczow.service_save_picture.impl.di.ServiceSavePictureFeatureDependencies
import com.sergokuzneczow.settings.impl.di.SettingsFeatureDependencies
import com.sergokuzneczow.splash.impl.di.SplashFeatureDependencies
import com.sergokuzneczow.suitable_pictures.impl.di.SuitablePicturesFeatureDependencies

public class PixelsApplication : Application(),
    HomeFeatureDependencies.Contract,
    SettingsFeatureDependencies.Contract,
    SuitablePicturesFeatureDependencies.Contract,
    DialogPageFilterDependencies.Contract,
    BottomSheetPageFilterDependencies.Contract,
    SearchSuitablePicturesDependencies.Contract,
    SelectedPictureFeatureDependencies.Contract,
    BottomSheetPictureInformationFeatureDependencies.Contract,
    SplashFeatureDependencies.Contract,
    ServiceSavePictureFeatureDependencies.Contract,
    ApplicationSetupFeatureDependencies.Contract {

    internal val pixelsComponent: PixelsComponent by lazy {
        DaggerPixelsComponent.builder()
            .setContext(this.applicationContext)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        pixelsComponent
    }

    override fun homeFeatureDependenciesProvide(): HomeFeatureDependencies = pixelsComponent

    override fun settingsFeatureDependenciesProvide(): SettingsFeatureDependencies = pixelsComponent

    override fun suitablePicturesFeatureDependenciesProvider(): SuitablePicturesFeatureDependencies = pixelsComponent

    override fun dialogPageFilterDependenciesProvider(): DialogPageFilterDependencies = pixelsComponent

    override fun bottomSheetPageFilterDependenciesProvider(): BottomSheetPageFilterDependencies = pixelsComponent

    override fun searchSuitablePicturesDependenciesProvider(): SearchSuitablePicturesDependencies = pixelsComponent

    override fun selectedPictureFeatureDependenciesProvider(): SelectedPictureFeatureDependencies = pixelsComponent

    override fun bottomSheetPictureInformationFeatureDependenciesProvider(): BottomSheetPictureInformationFeatureDependencies = applicationComponent

    override fun splashFeatureDependenciesProvide(): SplashFeatureDependencies = applicationComponent

    override fun serviceSavePictureFeatureDependenciesProvider(): ServiceSavePictureFeatureDependencies = applicationComponent

    override fun applicationSetupFeatureDependencies(): ApplicationSetupFeatureDependencies = applicationComponent
}