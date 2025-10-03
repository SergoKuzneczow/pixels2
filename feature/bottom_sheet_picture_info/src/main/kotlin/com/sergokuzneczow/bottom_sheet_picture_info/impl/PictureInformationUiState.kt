package com.sergokuzneczow.bottom_sheet_picture_info.impl

import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Tag

internal sealed interface PictureInformationUiState {

    data object Loading : PictureInformationUiState

    data class Success(
        val pictureKey: String,
        val picturePath: String,
        val tags: List<Tag>,
        val colors: List<Color>,
        val pictureSavingUiState: PictureSavingUiState,
    ) : PictureInformationUiState

    sealed interface PictureSavingUiState {
        data object Prepared : PictureSavingUiState
        data object Loading : PictureSavingUiState
        data object Saving : PictureSavingUiState
        data object Error : PictureSavingUiState
    }
}