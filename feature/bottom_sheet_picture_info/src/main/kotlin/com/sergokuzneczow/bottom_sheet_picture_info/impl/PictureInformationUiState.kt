package com.sergokuzneczow.bottom_sheet_picture_info.impl

import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Tag

internal sealed interface PictureInformationUiState {

    data object Loading : PictureInformationUiState

    data class Success(
        val savePictureButtonUiState: SavePictureButtonUiState,
        val likeThisButtonUiState: LikeThisButtonUiState,
        val tagsListUiState: TagsListUiState,
        val colorsListUiState: ColorsListUiState,
    ) : PictureInformationUiState
}

internal sealed interface SavePictureButtonUiState {
    val picturePath: String
    data class Prepared(override val picturePath: String) : SavePictureButtonUiState
    data class Loading(override val picturePath: String) : SavePictureButtonUiState
    data class Saved(
        override val picturePath: String,
        val uri: String
    ) : SavePictureButtonUiState
}

internal sealed interface LikeThisButtonUiState {
    data object Loading : LikeThisButtonUiState
    data object Empty : LikeThisButtonUiState
    data class Success(val pictureKey: String) : LikeThisButtonUiState
}

internal sealed interface TagsListUiState {
    data object Loading : TagsListUiState
    data object Empty : TagsListUiState
    data class Success(val tags: List<Tag>) : TagsListUiState
}

internal sealed interface ColorsListUiState {
    data object Loading : ColorsListUiState
    data object Empty : ColorsListUiState
    data class Success(val colors: List<Color>) : ColorsListUiState
}