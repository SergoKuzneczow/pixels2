package com.sergokuzneczow.pixels2

import android.app.Application
import com.sergokuzneczow.home.impl.di.HomeFeatureDependencies
import com.sergokuzneczow.pixels2.di.DaggerPixelsComponent
import com.sergokuzneczow.pixels2.di.PixelsComponent
import com.sergokuzneczow.settings.impl.di.SettingsFeatureDependencies
import com.sergokuzneczow.suitable_pictures.impl.di.SuitablePicturesFeatureDependencies

public class PixelsApplication : Application(),
    HomeFeatureDependencies.Contract,
    SettingsFeatureDependencies.Contract,
    SuitablePicturesFeatureDependencies.Contract {

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
}