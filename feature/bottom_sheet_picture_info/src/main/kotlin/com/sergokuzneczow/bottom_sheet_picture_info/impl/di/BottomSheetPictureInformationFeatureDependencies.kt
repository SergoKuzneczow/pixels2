package com.sergokuzneczow.bottom_sheet_picture_info.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_picture_with_relations_use_case.GetPictureWithRelationsCase
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi

public interface BottomSheetPictureInformationFeatureDependencies {
    public val getPictureWithRelationsCase: GetPictureWithRelationsCase
    public val getFirstPageKeyUseCase: GetFirstPageKeyUseCase
    public val imageLoaderApi: ImageLoaderApi
    public val storageRepositoryApi: StorageRepositoryApi

    public interface Contract {
        public fun bottomSheetPictureInformationFeatureDependenciesProvider(): BottomSheetPictureInformationFeatureDependencies
    }
}

internal val Context.dependenciesProvider: BottomSheetPictureInformationFeatureDependencies.Contract
    get() = when (this) {
        is BottomSheetPictureInformationFeatureDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement SearchSuitablePicturesDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }