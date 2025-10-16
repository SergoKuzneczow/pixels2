package com.sergokuzneczow.settings.impl

import android.app.Application
import androidx.lifecycle.AndroidViewModel

internal class SettingsScreenViewModel(application: Application) : AndroidViewModel(application) {

//    private val settingScreenComponent: SettingScreenComponent by lazy {
//        if (application is SettingScreenDependencies.Contract) {
//            DaggerSettingScreenComponent.builder()
//                .setDep((application as SettingScreenDependencies.Contract).settingScreenDependenciesProvide())
//                .build()
//        } else throw IllegalStateException("Application impl must implement HomeScreenDependencies.Contract.")
//    }
//
//    init {
//        settingScreenComponent.inject(this)
//    }
}