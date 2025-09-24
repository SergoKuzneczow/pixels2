package com.sergokuzneczow.search_suitable_pictures.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.PageRepositoryApi


public interface SearchSuitablePicturesDependencies {

    public val pageRepositoryApi: PageRepositoryApi

    public interface Contract {
        public fun searchSuitablePicturesDependenciesProvider(): SearchSuitablePicturesDependencies
    }
}

internal val Context.dependenciesProvider: SearchSuitablePicturesDependencies.Contract
    get() = when (this) {
        is SearchSuitablePicturesDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement SearchSuitablePicturesDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }