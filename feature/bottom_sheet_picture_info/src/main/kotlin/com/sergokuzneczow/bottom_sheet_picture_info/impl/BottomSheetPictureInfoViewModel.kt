package com.sergokuzneczow.bottom_sheet_picture_info.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.bottom_sheet_picture_info.impl.di.BottomSheetPictureInformationFeatureComponent
import com.sergokuzneczow.bottom_sheet_picture_info.impl.di.DaggerBottomSheetPictureInformationFeatureComponent
import com.sergokuzneczow.bottom_sheet_picture_info.impl.di.dependenciesProvider
import com.sergokuzneczow.domain.get_first_page_key.GetFirstPageKey
import com.sergokuzneczow.domain.get_picture_with_relations_use_case.GetPictureWithRelationsCase
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import com.sergokuzneczow.utilities.logger.log
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

internal class BottomSheetPictureInfoViewModel(
    @NonUiContext context: Context,
    private val pictureKey: String,
) : ViewModel() {

    @Inject
    lateinit var getPictureWithRelationsCase: GetPictureWithRelationsCase

    @Inject
    lateinit var getFirstPageKey: GetFirstPageKey

    @Inject
    lateinit var imageLoaderApi: ImageLoaderApi

    @Inject
    lateinit var storageRepositoryApi: StorageRepositoryApi

    private val selectedPictureFeatureComponent: BottomSheetPictureInformationFeatureComponent = DaggerBottomSheetPictureInformationFeatureComponent.builder()
        .setDep(context.dependenciesProvider.bottomSheetPictureInformationFeatureDependenciesProvider())
        .build()

    private val pictureInformationUiState: MutableStateFlow<PictureInformationUiState> = MutableStateFlow(PictureInformationUiState.Loading)

    init {
        selectedPictureFeatureComponent.inject(this)

        getPictureWithRelationsCase.execute(
            pictureKey = pictureKey,
            coroutineScope = viewModelScope,
        ).onEach {
            log(tag = "SelectedPictureViewModel") { "getPictureWithRelationsCase.execute().onEach().it=$it)" }
            it.onSuccess { pictureWithRelations ->
                pictureInformationUiState.emit(
                    PictureInformationUiState.Success(
                        pictureKey = pictureWithRelations.picture.key,
                        picturePath = pictureWithRelations.picture.path,
                        tags = pictureWithRelations.tags,
                        colors = pictureWithRelations.colors,
                        pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Prepared,
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    internal fun getPictureInformationUiState(): StateFlow<PictureInformationUiState> = pictureInformationUiState.asStateFlow()

    internal fun getPageKey(pageQuery: PageQuery, pageFilter: PageFilter, completed: (pageKey: Long) -> Unit) {
        viewModelScope.launch {
            val pageKey: Long? = getFirstPageKey.execute(pageQuery = pageQuery, pageFilter = pageFilter)
            pageKey?.let { pageKey -> completed.invoke(pageKey) }
        }
    }

    internal fun savePicture(picturePath: String) {
        viewModelScope.launch(Dispatchers.Default) {
            imageLoaderApi.loadBitmap(
                path = picturePath,
                onStart = {
                    viewModelScope.launch {
                        val s2 = pictureInformationUiState.value
                        if (s2 is PictureInformationUiState.Success)
                            pictureInformationUiState.emit(s2.copy(pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Loading))
                    }
                },
                onSuccess = {
                    storageRepositoryApi.savePictureBitmapToPictureDir(
                        bitmap = it,
                        onStart = { },
                        onSuccess = {
                            viewModelScope.launch {
                                val s1 = pictureInformationUiState.value
                                if (s1 is PictureInformationUiState.Success)
                                    pictureInformationUiState.emit(s1.copy(pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Success))
                                delay(1.seconds)
                                val s2 = pictureInformationUiState.value
                                if (s2 is PictureInformationUiState.Success)
                                    pictureInformationUiState.emit(s2.copy(pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Prepared))
                            }
                        },
                        onFailure = { throwable ->
                            viewModelScope.launch {
                                val s1 = pictureInformationUiState.value
                                if (s1 is PictureInformationUiState.Success)
                                    pictureInformationUiState.emit(s1.copy(pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Error(throwable)))
                                delay(1.seconds)
                                val s2 = pictureInformationUiState.value
                                if (s2 is PictureInformationUiState.Success)
                                    pictureInformationUiState.emit(s2.copy(pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Prepared))
                            }
                        }
                    )
                },
                onError = { throwable ->
                    viewModelScope.launch {
                        val s1 = pictureInformationUiState.value
                        if (s1 is PictureInformationUiState.Success)
                            pictureInformationUiState.emit(s1.copy(pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Error(throwable)))
                        delay(1.seconds)
                        val s2 = pictureInformationUiState.value
                        if (s2 is PictureInformationUiState.Success)
                            pictureInformationUiState.emit(s2.copy(pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Prepared))
                    }
                }
            )
        }
    }

    internal class Factory(
        @NonUiContext private val context: Context,
        private val pictureKey: String,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BottomSheetPictureInfoViewModel::class.java)) {
                return BottomSheetPictureInfoViewModel(
                    context = context,
                    pictureKey = pictureKey,
                ) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}