package com.sergokuzneczow.selected_picture.impl.di

import android.app.Application
import android.content.Context


public interface SelectedPictureFeatureDependencies {

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