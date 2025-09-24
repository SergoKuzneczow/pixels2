package com.sergokuzneczow.selected_picture.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import com.sergokuzneczow.selected_picture.impl.di.DaggerSelectedPictureFeatureComponent
import com.sergokuzneczow.selected_picture.impl.di.SelectedPictureFeatureComponent
import com.sergokuzneczow.selected_picture.impl.di.dependenciesProvider

internal class SelectedPictureViewModel(
    @NonUiContext context: Context,
) : ViewModel() {

    private val selectedPictureFeatureComponent: SelectedPictureFeatureComponent = DaggerSelectedPictureFeatureComponent.builder()
        .setDep(context.dependenciesProvider.selectedPictureFeatureDependenciesProvider())
        .build()

    init {
        selectedPictureFeatureComponent.inject(this)
    }
}