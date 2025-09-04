package com.sergokuzneczow.network.impl.retrofit.models

import com.google.gson.annotations.SerializedName
import com.sergokuzneczow.models.Picture

internal data class PicturesPageRemoteModel(
    @SerializedName("data") val data: List<PicturePreviewRemoteModel>,
    val meta: Meta,
) {
    data class PicturePreviewRemoteModel(
        val id: String,
        val url: String,
        @SerializedName("short_url")
        val shortUrl: String,
        val views: Int,
        val favorites: Int,
        val source: String,
        val purity: String,
        val category: String,
        @SerializedName("dimension_x")
        val dimensionX: Int,
        @SerializedName("dimension_y")
        val dimensionY: Int,
        val resolution: String,
        val ratio: String,
        @SerializedName("file_size")
        val fileSize: Int,
        @SerializedName("file_type")
        val fileType: String,
        @SerializedName("created_at")
        val createAt: String,
        val colors: List<String>,
        val path: String,
        val thumbs: Thumbs
    ) {

        data class Thumbs(
            val large: String,
            val original: String,
            val small: String
        )
    }

    data class Meta(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        @SerializedName("per_page")
        val perPage: Int,
        val total: Int,
        val query: Any?,
        val seed: String?,
    )
}

internal fun PicturesPageRemoteModel.PicturePreviewRemoteModel.toPicture(): Picture = Picture(
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
    tags = emptyList(),
    colors = emptyList(),
)

internal fun List<PicturesPageRemoteModel.PicturePreviewRemoteModel>.toPictures(): List<Picture> = this.map { it.toPicture() }