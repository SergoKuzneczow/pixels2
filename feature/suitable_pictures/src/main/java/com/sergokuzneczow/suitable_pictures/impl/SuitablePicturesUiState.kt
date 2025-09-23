package com.sergokuzneczow.suitable_pictures.impl

import com.sergokuzneczow.models.Picture

internal sealed interface SuitablePicturesUiState {

    data object Empty : SuitablePicturesUiState

    class Default private constructor(val suitablePictures: List<SuitablePicture?>) : SuitablePicturesUiState {
        constructor() : this(suitablePictures = emptyList())
    }

    data class Success(val suitablePictures: List<SuitablePicture?>) : SuitablePicturesUiState

    data class SuitablePicture(
        val pictureKey: String,
        val previewPath: String,
    )
}

internal fun List<Picture?>.toSuitablePictures(): List<SuitablePicturesUiState.SuitablePicture?> {
    return this.map { picture ->
        picture?.let { pictureNotNull ->
            SuitablePicturesUiState.SuitablePicture(
                pictureKey = pictureNotNull.key,
                previewPath = pictureNotNull.original,
            )
        }
    }
}
