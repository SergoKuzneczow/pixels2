package com.sergokuzneczow.database.impl.framework.models

import com.sergokuzneczow.models.PageFilter
import kotlinx.serialization.Serializable

@Serializable
internal data class PicturePuritiesLocalModel(
    val sfw: Boolean,
    val sketchy: Boolean,
    val nsfw: Boolean,
)

internal fun PageFilter.PicturePurities.toPicturePuritiesLocalModel(): PicturePuritiesLocalModel {
    return PicturePuritiesLocalModel(
        sfw = this.sfw,
        sketchy = this.sketchy,
        nsfw = this.nsfw,
    )
}

internal fun PicturePuritiesLocalModel.toPicturePuritiesLocalModel(): PageFilter.PicturePurities {
    return PageFilter.PicturePurities(
        sfw = this.sfw,
        sketchy = this.sketchy,
        nsfw = this.nsfw,
    )
}