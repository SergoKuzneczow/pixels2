package com.sergokuzneczow.selected_picture.impl

internal sealed interface SelectedPictureUiState {

    data object Loading : SelectedPictureUiState

    data class Success(
        val pictureKey: String,
        val picturePath: String,
        val curtainVisible: Boolean,
        val infoFabVisible: Boolean,
    ) : SelectedPictureUiState
}