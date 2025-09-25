package com.sergokuzneczow.database.impl.framework.models

import com.sergokuzneczow.models.Tag
import kotlinx.serialization.Serializable

@Serializable
internal enum class TagPurityLocalModel {
    SFW,
    SKETCHY,
    NSFW,
}

internal fun Tag.TagPurity.toTagPurityLocalModel(): TagPurityLocalModel {
    return when (this) {
        Tag.TagPurity.SFW -> TagPurityLocalModel.SFW
        Tag.TagPurity.SKETCHY -> TagPurityLocalModel.SKETCHY
        Tag.TagPurity.NSFW -> TagPurityLocalModel.NSFW
    }
}

internal fun TagPurityLocalModel.toTagPurity(): Tag.TagPurity {
    return when (this) {
        TagPurityLocalModel.SFW -> Tag.TagPurity.SFW
        TagPurityLocalModel.SKETCHY -> Tag.TagPurity.SKETCHY
        TagPurityLocalModel.NSFW -> Tag.TagPurity.NSFW
    }
}