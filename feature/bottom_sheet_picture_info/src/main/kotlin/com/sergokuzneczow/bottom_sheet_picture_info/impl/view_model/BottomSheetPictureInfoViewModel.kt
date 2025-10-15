package com.sergokuzneczow.bottom_sheet_picture_info.impl.view_model

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.bottom_sheet_picture_info.impl.ColorsListUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.LikeThisButtonUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationIntent
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationIntent.FailedSavePicture
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationIntent.SavingPicture
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationIntent.SuccessSavePicture
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.SavePictureButtonUiState.*
import com.sergokuzneczow.bottom_sheet_picture_info.impl.TagsListUiState
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase.Answer.AnswerState.CACHED
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase.Answer.AnswerState.UPDATED
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class BottomSheetPictureInfoViewModel(
    pictureKey: String,
    getPictureWithRelations2UseCase: GetPictureWithRelations2UseCase,
    private val getFirstPageKeyUseCase: GetFirstPageKeyUseCase,
    private val imageLoaderApi: ImageLoaderApi,
    private val storageRepositoryApi: StorageRepositoryApi,
) : ViewModel() {

    @Volatile
    private var currentUiState: PictureInformationUiState = PictureInformationUiState.Loading

    private var currentUiStateMutex: Mutex = Mutex()

    private val sourceFlow: Flow<PictureInformationUiState> =
        getPictureWithRelations2UseCase.execute(viewModelScope, pictureKey)
            .map { result: Result<GetPictureWithRelations2UseCase.Answer> ->
                currentUiStateMutex.withLock {
                    result.onSuccess {
                        currentUiState = when (it.state) {
                            CACHED -> {
                                PictureInformationUiState.Success(
                                    savePictureButtonUiState = Prepared(it.data.picture.path),
                                    likeThisButtonUiState = LikeThisButtonUiState.Success(it.data.picture.key),
                                    tagsListUiState = if (it.data.tags.isNotEmpty()) TagsListUiState.Success(it.data.tags) else TagsListUiState.Loading,
                                    colorsListUiState = if (it.data.colors.isNotEmpty()) ColorsListUiState.Success(it.data.colors) else ColorsListUiState.Loading,
                                )
                            }

                            UPDATED -> {
                                PictureInformationUiState.Success(
                                    savePictureButtonUiState = Prepared(it.data.picture.path),
                                    likeThisButtonUiState = LikeThisButtonUiState.Success(it.data.picture.key),
                                    tagsListUiState = if (it.data.tags.isNotEmpty()) TagsListUiState.Success(it.data.tags) else TagsListUiState.Empty,
                                    colorsListUiState = if (it.data.colors.isNotEmpty()) ColorsListUiState.Success(it.data.colors) else ColorsListUiState.Empty,
                                )
                            }
                        }
                    }.onFailure {}
                    currentUiState
                }
            }.flowOn(Dispatchers.IO)

    private val intentListener: MutableSharedFlow<PictureInformationIntent> = MutableSharedFlow()

    private val intentListenerFlow: Flow<PictureInformationUiState> =
        intentListener.map { intent ->
            currentUiStateMutex.withLock {
                currentUiState = when (intent) {
                    is SavingPicture -> {
                        if (currentUiState is PictureInformationUiState.Success) {
                            savePicture(intent.picturePath)
                            (currentUiState as PictureInformationUiState.Success).copy(savePictureButtonUiState = Loading(intent.picturePath))
                        } else currentUiState
                    }

                    is FailedSavePicture -> {
                        if (currentUiState is PictureInformationUiState.Success) {
                            (currentUiState as PictureInformationUiState.Success).copy(savePictureButtonUiState = Prepared(intent.picturePath))
                        } else currentUiState
                    }

                    is SuccessSavePicture -> {
                        if (currentUiState is PictureInformationUiState.Success) {
                            (currentUiState as PictureInformationUiState.Success).copy(savePictureButtonUiState = Saved(intent.picturePath, intent.uri))
                        } else currentUiState
                    }

                    is PictureInformationIntent.SearchPageKey -> {
                        getPageKey(
                            pageQuery = intent.pageQuery,
                            pageFilter = intent.pageFilter,
                            completedBlock = intent.completedBlock,
                        )
                        currentUiState
                    }
                }
                currentUiState
            }
        }

    internal val uiState: StateFlow<PictureInformationUiState> = merge(sourceFlow, intentListenerFlow)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PictureInformationUiState.Loading
        )

    internal fun setIntent(intent: PictureInformationIntent) {
        viewModelScope.launch { intentListener.emit(intent) }
    }

    private fun getPageKey(pageQuery: PageQuery, pageFilter: PageFilter, completedBlock: (pageKey: Long) -> Unit) {
        viewModelScope.launch {
            val pageKey: Long? = getFirstPageKeyUseCase.execute(pageQuery = pageQuery, pageFilter = pageFilter)
            pageKey?.let { pageKey -> completedBlock.invoke(pageKey) }
        }
    }

    private fun savePicture(picturePath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val loadResult: Result<Bitmap> = imageLoaderApi.loadBitmapAwait(picturePath)
            if (loadResult.isFailure) {
                setIntent(FailedSavePicture(picturePath))
                cancel()
            } else {
                val bitmap: Bitmap = loadResult.getOrThrow()
                val saveResult: Result<Uri> = storageRepositoryApi.savePictureAwait(bitmap)
                if (saveResult.isFailure) {
                    setIntent(FailedSavePicture(picturePath))
                    cancel()
                } else {
                    val uri: Uri = saveResult.getOrThrow()
                    setIntent(SuccessSavePicture(picturePath, uri.path.toString()))
                }
            }
        }
    }
}