package com.sergokuzneczow.dialog_page_filter.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase

public interface DialogPageFilterDependencies {

    public val getFirstPageKeyUseCase: GetFirstPageKeyUseCase

    public val getPage: GetPage

    public interface Contract {

        public fun dialogPageFilterDependenciesProvider(): DialogPageFilterDependencies
    }
}

internal val Context.dependenciesProvider: DialogPageFilterDependencies.Contract
    get() = when (this) {
        is DialogPageFilterDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement DialogPageFilterDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }