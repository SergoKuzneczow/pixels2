package com.sergokuzneczow.search_suitable_pictures.impl.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.search_suitable_pictures.impl.di.DaggerSearchSuitablePicturesComponent
import com.sergokuzneczow.search_suitable_pictures.impl.di.dependencies
import jakarta.inject.Inject

internal class SearchSuitablePicturesScreenDependenciesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var pageRepositoryApi: PageRepositoryApi

    init {
        DaggerSearchSuitablePicturesComponent.builder()
            .setDep(application.applicationContext.dependencies)
            .build()
            .inject(this)
    }
}