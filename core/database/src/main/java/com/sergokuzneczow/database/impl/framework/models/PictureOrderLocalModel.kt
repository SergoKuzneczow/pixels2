package com.sergokuzneczow.database.impl.framework.models

import com.sergokuzneczow.models.PageFilter
import kotlinx.serialization.Serializable

@Serializable
internal enum class PictureOrderLocalModel {
    DESC,
    ASC;
}

internal fun PageFilter.PictureOrder.toPictureOrderLocalModel(): PictureOrderLocalModel {
    return when (this) {
        PageFilter.PictureOrder.DESC -> PictureOrderLocalModel.DESC
        PageFilter.PictureOrder.ASC -> PictureOrderLocalModel.ASC
    }
}

internal fun PictureOrderLocalModel.toPageFilterPictureOrder(): PageFilter.PictureOrder {
    return when (this) {
        PictureOrderLocalModel.DESC -> PageFilter.PictureOrder.DESC
        PictureOrderLocalModel.ASC -> PageFilter.PictureOrder.ASC
    }
}