package com.sergokuzneczow.database.impl.framework.models

import com.sergokuzneczow.models.PageFilter
import kotlinx.serialization.Serializable

@Serializable
internal data class PictureCategoriesLocalModel(
    val general: Boolean,
    val anime: Boolean,
    val people: Boolean,
)

internal fun PageFilter.PictureCategories.toPictureCategoriesLocalModel(): PictureCategoriesLocalModel {
    return PictureCategoriesLocalModel(
        general = this.general,
        anime = this.anime,
        people = this.people,
    )
}

internal fun PictureCategoriesLocalModel.toPageFilterPictureCategories(): PageFilter.PictureCategories {
    return PageFilter.PictureCategories(
        general = this.general,
        anime = this.anime,
        people = this.people,
    )
}