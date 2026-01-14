package com.sergokuzneczow.home.impl.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_home_screen_pager4_use_case.GetHomeScreenPager4UseCase
import com.sergokuzneczow.home.impl.di.DaggerHomeFeatureComponent
import com.sergokuzneczow.home.impl.di.dependenciesProvider
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.utilities.DispatchersApi
import jakarta.inject.Inject

internal class HomeScreenDependenciesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var imageLoaderApi: ImageLoaderApi

    @Inject
    lateinit var dispatchersApi: DispatchersApi

    @Inject
    lateinit var getHomeScreenPager4UseCase: GetHomeScreenPager4UseCase

    @Inject
    lateinit var getFirstPageKeyUseCase: GetFirstPageKeyUseCase

    init {
        DaggerHomeFeatureComponent.builder()
            .setDependencies(application.dependenciesProvider)
            .build()
            .inject(this)
    }
}