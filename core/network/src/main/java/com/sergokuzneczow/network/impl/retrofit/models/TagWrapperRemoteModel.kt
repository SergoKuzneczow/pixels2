package com.sergokuzneczow.network.impl.retrofit.models

import com.google.gson.annotations.SerializedName
import com.sergokuzneczow.models.Tag

internal data class TagWrapperRemoteModel(
    val data: TagRemoteModel,
) {
    data class TagRemoteModel(
        val id: Int,
        val name: String,
        val alias: String,
        @SerializedName("category_id") val categoryId: Int,
        @SerializedName("category") val categoryName: String,
        val purity: String,
        @SerializedName("created_at") val createdAt: String,
    )
}

internal fun TagWrapperRemoteModel.TagRemoteModel.toTag(): Tag = Tag(
    id = this.id,
    name = this.name,
    alias = this.alias,
    categoryId = this.categoryId,
    categoryName = this.categoryName,
    purity = this.purity.toTagPurity(),
    createdAt = this.createdAt,
)

private fun String.toTagPurity(): Tag.TagPurity {
    return when (this) {
        "sketchy" -> Tag.TagPurity.SKETCHY
        "nsfw" -> Tag.TagPurity.NSFW
        else -> Tag.TagPurity.SFW
    }
}


internal fun List<TagWrapperRemoteModel.TagRemoteModel>.toTags(): List<Tag> = this.map { it.toTag() }