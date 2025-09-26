package com.sergokuzneczow.selected_picture.impl

import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Tag

internal sealed interface SelectedPictureUiState {

    data object Loading : SelectedPictureUiState

    data class Success(
        val pictureKey: String,
        val picturePath: String,
    ) : SelectedPictureUiState
}