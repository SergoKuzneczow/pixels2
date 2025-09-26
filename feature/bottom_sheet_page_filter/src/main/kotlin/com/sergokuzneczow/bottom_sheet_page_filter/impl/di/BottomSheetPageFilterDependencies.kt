package com.sergokuzneczow.bottom_sheet_page_filter.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_first_page_key.GetFirstPageKey

public interface BottomSheetPageFilterDependencies {

    public val getFirstPageKey: GetFirstPageKey

    public val getPage: GetPage

    public interface Contract {

        public fun bottomSheetPageFilterDependenciesProvider(): BottomSheetPageFilterDependencies
    }
}

internal val Context.dependenciesProvider: BottomSheetPageFilterDependencies.Contract
    get() = when (this) {
        is BottomSheetPageFilterDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement BottomSheetPageFilterDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }