package com.sergokuzneczow.database.impl.framework.models

import com.sergokuzneczow.models.PageFilter
import kotlinx.serialization.Serializable

@Serializable
internal data class PictureColorLocalModel(val value: String)

private val ANY: PictureColorLocalModel = PictureColorLocalModel(value = "any")

internal fun PageFilter.PictureColor.toPictureColorLocalModel(): PictureColorLocalModel {
    return if (this.value.isEmpty()) ANY else PictureColorLocalModel(this.value)
}

internal fun PictureColorLocalModel.toPageFilterPictureColor(): PageFilter.PictureColor {
    return if (this == ANY) PageFilter.PictureColor("") else PageFilter.PictureColor(this.value)
}