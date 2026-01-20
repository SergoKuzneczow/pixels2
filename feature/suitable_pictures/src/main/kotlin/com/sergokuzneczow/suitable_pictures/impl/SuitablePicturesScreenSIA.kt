package com.sergokuzneczow.suitable_pictures.impl

import com.sergokuzneczow.suitable_pictures.impl.model.SuitablePicturesPage

internal sealed interface SuitablePicturesState {
    val title: String

    data class Loading(override val title: String = "") : SuitablePicturesState
    data class Success(
        override val title: String,
        val suitablePicturesPages: List<SuitablePicturesPage>,
    ) : SuitablePicturesState

    data class Empty(override val title: String) : SuitablePicturesState
}

internal sealed interface SuitablePicturesScreenIntent {
    data object GetNextPage : SuitablePicturesScreenIntent
    data object BackHome : SuitablePicturesScreenIntent
    data object ToPageFilter : SuitablePicturesScreenIntent
    data class ToSelectedPicture(val pictureKey: String) : SuitablePicturesScreenIntent
}

internal sealed interface SuitablePicturesScreenSideEffect {
    data object OnBackHome : SuitablePicturesScreenSideEffect
    data class OnToPageFilter(val pageKey: Long) : SuitablePicturesScreenSideEffect
    data class OnToSelectedPicture(val pictureKey: String) : SuitablePicturesScreenSideEffect
}