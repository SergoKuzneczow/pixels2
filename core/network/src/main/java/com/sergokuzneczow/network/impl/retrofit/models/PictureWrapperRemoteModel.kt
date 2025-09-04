package com.sergokuzneczow.network.impl.retrofit.models

import com.google.gson.annotations.SerializedName
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.network.impl.retrofit.models.TagWrapperRemoteModel.TagRemoteModel

internal data class PictureWrapperRemoteModel(
    @SerializedName("data") val data: PictureRemoteModel,
    val meta: Meta,
) {
    data class PictureRemoteModel(
        val id: String,
        val url: String,
        @SerializedName("short_url") val shortUrl: String,
        val uploader: Uploader,
        val views: Int,
        val favorites: Int,
        val source: String,
        val purity: String,
        val category: String,
        @SerializedName("dimension_x") val dimensionX: Int,
        @SerializedName("dimension_y") val dimensionY: Int,
        val resolution: String,
        val ratio: String,
        @SerializedName("file_size") val fileSize: Int,
        @SerializedName("file_type") val fileType: String,
        @SerializedName("created_at") val createAt: String,
        val colors: List<String>,
        val path: String,
        val thumbs: Thumbs,
        val tags: List<TagRemoteModel>,
    ) {

        data class Uploader(
            val username: String,
            val group: String,
            val avatar: Avatar,
        ) {

            data class Avatar(
                @SerializedName("200px") val px200: String,
                @SerializedName("128px") val px128: String,
                @SerializedName("32px") val px32: String,
                @SerializedName("20px") val px20: String,
            )
        }

        data class Thumbs(
            val large: String,
            val original: String,
            val small: String
        )
    }

    data class Meta(
        @SerializedName("current_page")
        val currentPage: Int?,
        @SerializedName("last_page")
        val lastPage: Int?,
        @SerializedName("per_page")
        val perPage: Int?,
        val total: Int?,
        val query: String?,
        val seed: String?,
    )
}

internal fun PictureWrapperRemoteModel.PictureRemoteModel.toPicture(): Picture = Picture(
    key = this.id,
    url = this.url,
    shortUrl = this.shortUrl,
    views = this.views,
    favorites = this.favorites,
    source = this.source,
    purity = this.purity,
    categories = this.category,
    dimensionX = this.dimensionX,
    dimensionY = this.dimensionY,
    resolution = this.resolution,
    ratio = this.ratio,
    fileSize = this.fileSize,
    fileType = this.fileType,
    createAt = this.createAt,
    path = this.path,
    large = this.thumbs.large,
    original = this.thumbs.original,
    small = this.thumbs.small,
    tags = this.tags.toTags(),
    colors = this.colors.toColors(),
)

internal fun List<PictureWrapperRemoteModel.PictureRemoteModel>.toPictures(): List<Picture> = this.map { it.toPicture() }

internal fun String.toColor(): Color = Color(this, this)

internal fun List<String>.toColors(): List<Color> = this.map { it.toColor() }