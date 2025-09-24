package com.sergokuzneczow.suitable_pictures.impl

import com.sergokuzneczow.models.Picture
import java.util.TreeMap

internal sealed interface SuitablePicturesUiState {

    data object Empty : SuitablePicturesUiState

    data object Loading : SuitablePicturesUiState

    data class Success(val suitablePicturesPages: List<SuitablePicturesPage>) : SuitablePicturesUiState

    data class SuitablePicturesPage(
        val items: List<SuitablePicture?>,
    )
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

internal fun TreeMap<Int, List<Picture?>>.toSuitablePicturesPages(): List<SuitablePicturesUiState.SuitablePicturesPage> {
    return this.entries.map { SuitablePicturesUiState.SuitablePicturesPage(it.value.toSuitablePictures()) }
}