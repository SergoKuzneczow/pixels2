package com.sergokuzneczow.suitable_pictures.impl.model

import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.models.Picture
import java.util.TreeMap

internal data class SuitablePicturesPage(
    val items: List<SuitablePicture?>,
) {
    data class SuitablePicture(
        val pictureKey: String,
        val previewPath: String,
    )
}

internal fun List<Picture?>.toSuitablePictures(): List<SuitablePicturesPage.SuitablePicture?> {
    return this.map { picture ->
        picture?.let { pictureNotNull ->
            SuitablePicturesPage.SuitablePicture(
                pictureKey = pictureNotNull.key,
                previewPath = pictureNotNull.original,
            )
        }
    }
}

internal fun TreeMap<Int, IPixelsPager4.Answer.Page<Picture?>>.toSuitablePicturesPages(): List<SuitablePicturesPage> {
    return this.entries.map { SuitablePicturesPage(it.value.data.toSuitablePictures()) }
}

internal fun List<SuitablePicturesPage>.hasPages(): Boolean {
    return this.firstOrNull(predicate = { it.items.isNotEmpty() }) != null
}