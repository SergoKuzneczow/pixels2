package com.sergokuzneczow.pixels2

import android.app.Application
import com.sergokuzneczow.home.di.HomeScreenDependencies
import com.sergokuzneczow.pixels2.di.DaggerPixelsComponent
import com.sergokuzneczow.pixels2.di.PixelsComponent
import com.sergokuzneczow.settings.di.SettingsScreenDependencies

public class PixelsApplication : Application(),
    HomeScreenDependencies.Contract,
    SettingsScreenDependencies.Contract {

    internal val pixelsComponent: PixelsComponent by lazy {
        DaggerPixelsComponent.builder()
            .setContext(this.applicationContext)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        pixelsComponent
    }

    override fun homeScreenDependenciesProvide(): HomeScreenDependencies = pixelsComponent

    override fun settingsScreenDependenciesProvide(): SettingsScreenDependencies = pixelsComponent
}