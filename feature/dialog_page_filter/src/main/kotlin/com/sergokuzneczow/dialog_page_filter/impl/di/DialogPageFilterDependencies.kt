package com.sergokuzneczow.dialog_page_filter.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.PageRepositoryApi

public interface DialogPageFilterDependencies {

    public val pageRepositoryApi: PageRepositoryApi

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