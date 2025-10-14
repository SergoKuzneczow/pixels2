package com.sergokuzneczow.selected_picture.impl.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelations2UseCase
import com.sergokuzneczow.selected_picture.impl.di.SelectedPictureFeatureComponent
import com.sergokuzneczow.selected_picture.impl.di.dependenciesProvider
import jakarta.inject.Inject

internal class SelectedPictureViewModelFactory(
    context: Context,
    private val pictureKey: String,
) : ViewModelProvider.Factory {

    @Inject
    lateinit var getPictureWithRelations2UseCase: GetPictureWithRelations2UseCase

    init {
        SelectedPictureFeatureComponent.Instance
            .get(context.dependenciesProvider.selectedPictureFeatureDependenciesProvider())
            ?.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectedPictureViewModel::class.java)) {
            return SelectedPictureViewModel(
                pictureKey = pictureKey,
                getPictureWithRelations2UseCase = getPictureWithRelations2UseCase,
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}