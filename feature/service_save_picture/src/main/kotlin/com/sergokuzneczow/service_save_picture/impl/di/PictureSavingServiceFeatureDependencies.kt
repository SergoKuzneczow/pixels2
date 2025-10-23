package com.sergokuzneczow.service_save_picture.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi

public interface ServiceSavePictureFeatureDependencies {

    public val imageLoaderApi: ImageLoaderApi

    public val storageRepositoryApi: StorageRepositoryApi

    public interface Contract {
        public fun serviceSavePictureFeatureDependenciesProvider(): ServiceSavePictureFeatureDependencies
    }
}

internal val Context.dependenciesProvider: ServiceSavePictureFeatureDependencies.Contract
    get() = when (this) {
        is ServiceSavePictureFeatureDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement SearchSuitablePicturesDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }