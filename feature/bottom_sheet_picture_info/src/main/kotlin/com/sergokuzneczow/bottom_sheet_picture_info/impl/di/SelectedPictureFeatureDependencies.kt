package com.sergokuzneczow.bottom_sheet_picture_info.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.get_first_page_key.GetFirstPageKey
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelationsCase

public interface BottomSheetPictureInformationFeatureDependencies {

    public val getPictureWithRelationsCase: GetPictureWithRelationsCase

    public val getFirstPageKey: GetFirstPageKey

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