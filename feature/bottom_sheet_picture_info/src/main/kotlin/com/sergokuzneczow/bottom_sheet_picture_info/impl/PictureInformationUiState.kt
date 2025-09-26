package com.sergokuzneczow.bottom_sheet_picture_info.impl

import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Tag

internal sealed interface PictureInformationUiState {

    data object Loading : PictureInformationUiState

    data class Success(
        val tags: List<Tag>,
        val colors: List<Color>,
    ) : PictureInformationUiState
}