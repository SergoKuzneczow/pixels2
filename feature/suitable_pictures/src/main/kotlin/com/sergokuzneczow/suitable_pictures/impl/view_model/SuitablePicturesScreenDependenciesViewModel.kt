package com.sergokuzneczow.suitable_pictures.impl.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPager4UseCase
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.suitable_pictures.impl.di.DaggerSuitablePicturesFeatureComponent
import com.sergokuzneczow.suitable_pictures.impl.di.dependencies
import com.sergokuzneczow.utilities.DispatchersApi
import jakarta.inject.Inject

internal class SuitablePicturesScreenDependenciesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var dispatchersApi: DispatchersApi

    @Inject
    lateinit var getPage: GetPage

    @Inject
    lateinit var getSuitablePicturesScreenPager4UseCase: GetSuitablePicturesScreenPager4UseCase

    @Inject
    lateinit var imageLoaderApi: ImageLoaderApi

    init {
        DaggerSuitablePicturesFeatureComponent.builder()
            .setDependencies(application.applicationContext.dependencies)
            .build()
            .inject(this)
    }
}