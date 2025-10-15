package com.sergokuzneczow.bottom_sheet_picture_info.impl.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.bottom_sheet_picture_info.impl.di.BottomSheetPictureInformationFeatureComponent
import com.sergokuzneczow.bottom_sheet_picture_info.impl.di.dependenciesProvider
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase
import com.sergokuzneczow.domain.get_picture_with_relations_use_case.GetPictureWithRelationsCase
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import jakarta.inject.Inject


internal class BottomSheetPictureInfoViewModelFactory(
    context: Context,
    private val pictureKey: String,
) : ViewModelProvider.Factory {

    @Inject
    lateinit var getPictureWithRelations2UseCase: GetPictureWithRelations2UseCase

    @Inject
    lateinit var getFirstPageKeyUseCase: GetFirstPageKeyUseCase

    @Inject
    lateinit var imageLoaderApi: ImageLoaderApi

    @Inject
    lateinit var storageRepositoryApi: StorageRepositoryApi

    init {
        BottomSheetPictureInformationFeatureComponent.Instance
            .get(context.dependenciesProvider.bottomSheetPictureInformationFeatureDependenciesProvider())
            .inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomSheetPictureInfoViewModel::class.java)) {
            return BottomSheetPictureInfoViewModel(
                pictureKey = pictureKey,
                getPictureWithRelations2UseCase = getPictureWithRelations2UseCase,
                getFirstPageKeyUseCase = getFirstPageKeyUseCase,
                imageLoaderApi = imageLoaderApi,
                storageRepositoryApi = storageRepositoryApi,
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}
