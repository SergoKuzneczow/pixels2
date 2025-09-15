package com.sergokuzneczow.database.impl.framework.models

import com.sergokuzneczow.models.PageFilter
import kotlinx.serialization.Serializable

@Serializable
internal enum class PictureSortingLocalModel {
    VIEWS,
    RANDOM,
    FAVORITES,
    TOP_LIST,
    DATE_ADDED;
}

internal fun PageFilter.PictureSorting.toPictureSortingLocalModel(): PictureSortingLocalModel {
    return when (this) {
        PageFilter.PictureSorting.VIEWS -> PictureSortingLocalModel.VIEWS
        PageFilter.PictureSorting.RANDOM -> PictureSortingLocalModel.RANDOM
        PageFilter.PictureSorting.FAVORITES -> PictureSortingLocalModel.FAVORITES
        PageFilter.PictureSorting.TOP_LIST -> PictureSortingLocalModel.TOP_LIST
        PageFilter.PictureSorting.DATE_ADDED -> PictureSortingLocalModel.DATE_ADDED
    }
}

internal fun PictureSortingLocalModel.toPageFilterPictureSorting(): PageFilter.PictureSorting {
    return when(this){
        PictureSortingLocalModel.VIEWS -> PageFilter.PictureSorting.VIEWS
        PictureSortingLocalModel.RANDOM -> PageFilter.PictureSorting.RANDOM
        PictureSortingLocalModel.FAVORITES -> PageFilter.PictureSorting.FAVORITES
        PictureSortingLocalModel.TOP_LIST -> PageFilter.PictureSorting.TOP_LIST
        PictureSortingLocalModel.DATE_ADDED -> PageFilter.PictureSorting.DATE_ADDED
    }
}