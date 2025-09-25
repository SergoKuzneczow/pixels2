package com.sergokuzneczow.selected_picture.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelationsCase
import com.sergokuzneczow.repository.api.PageRepositoryApi


public interface SelectedPictureFeatureDependencies {

    public val getPictureWithRelationsCase: GetPictureWithRelationsCase

    public val pageRepositoryApi: PageRepositoryApi

    public interface Contract {
        public fun selectedPictureFeatureDependenciesProvider(): SelectedPictureFeatureDependencies
    }
}

internal val Context.dependenciesProvider: SelectedPictureFeatureDependencies.Contract
    get() = when (this) {
        is SelectedPictureFeatureDependencies.Contract -> this
        is Application -> throw IllegalArgumentException("Application must implement SearchSuitablePicturesDependencies.Contract.")
        else -> this.applicationContext.dependenciesProvider
    }