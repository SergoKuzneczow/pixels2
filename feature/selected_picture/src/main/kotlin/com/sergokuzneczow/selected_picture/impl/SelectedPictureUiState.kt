package com.sergokuzneczow.selected_picture.impl

internal sealed interface SelectedPictureUiState {

    val exceptionMessage: String?

    data class Loading(
        override val exceptionMessage: String? = null,
    ) : SelectedPictureUiState

    data class Success(
        val pictureKey: String,
        val picturePath: String,
        val curtainVisible: Boolean,
        val infoFabVisible: Boolean,
        override val exceptionMessage: String? = null,
    ) : SelectedPictureUiState
}