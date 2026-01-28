package com.sergokuzneczow.bottom_sheet_page_filter.impl.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sergokuzneczow.bottom_sheet_page_filter.impl.di.DaggerBottomSheetPageFilterComponent
import com.sergokuzneczow.bottom_sheet_page_filter.impl.di.dependencies
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.utilities.DispatchersApi
import jakarta.inject.Inject

internal class BottomSheetPageDependenciesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var dispatchersApi: DispatchersApi

    @Inject
    lateinit var getFirstPageKeyUseCase: GetFirstPageKeyUseCase

    @Inject
    lateinit var getPage: GetPage

    init {
        DaggerBottomSheetPageFilterComponent.builder()
            .setDependencies(application.applicationContext.dependencies)
            .build()
            .inject(this)
    }
}