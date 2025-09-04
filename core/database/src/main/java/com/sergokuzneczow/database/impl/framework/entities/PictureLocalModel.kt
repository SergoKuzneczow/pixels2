package com.sergokuzneczow.database.impl.framework.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.sergokuzneczow.database.impl.framework.entities.ColorLocalModel.Companion.COLOR_KEY_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PictureColorCrossRef.Companion.COLOR_KEY_X_REF_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PictureLocalModel.Companion.PICTURES_TABLE_NAME
import com.sergokuzneczow.database.impl.framework.entities.PictureLocalModel.Companion.PICTURE_KEY_COLUMN_NAME
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.Tag
import com.sergokuzneczow.database.impl.framework.entities.PictureTagCrossRef.Companion.TAG_KEY_X_REF_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.TagLocalModel.Companion.TAG_KEY_COLUMN_NAME

@Entity(
    tableName = PICTURES_TABLE_NAME,
    primaryKeys = [PICTURE_KEY_COLUMN_NAME],
)
public data class PictureLocalModel(
    @ColumnInfo(PICTURE_KEY_COLUMN_NAME) val key: String,
    @ColumnInfo(URL_COLUMN_NAME) val url: String,
    @ColumnInfo(SHORT_URL_COLUMN_NAME) val shortUrl: String,
    @ColumnInfo(VIEWS_COLUMN_NAME) val views: Int,
    @ColumnInfo(FAVORITES_COLUMN_NAME) val favorites: Int,
    @ColumnInfo(SOURCE_COLUMN_NAME) val source: String,
    @ColumnInfo(PURITIES_COLUMN_NAME) val purity: String,
    @ColumnInfo(CATEGORIES_COLUMN_NAME) val categories: String,
    @ColumnInfo(DIMENSION_X_COLUMN_NAME) val dimensionX: Int,
    @ColumnInfo(DIMENSION_Y_COLUMN_NAME) val dimensionY: Int,
    @ColumnInfo(RESOLUTION_COLUMN_NAME) val resolution: String,
    @ColumnInfo(RATIO_COLUMN_NAME) val ratio: String,
    @ColumnInfo(FILE_SIZE_COLUMN_NAME) val fileSize: Int,
    @ColumnInfo(FILE_TYPE_COLUMN_NAME) val fileType: String,
    @ColumnInfo(CREATE_AT_COLUMN_NAME) val createAt: String,
    @ColumnInfo(PATH_COLUMN_NAME) val path: String,
    @ColumnInfo(LARGE_COLUMN_NAME) val large: String,
    @ColumnInfo(ORIGINAL_COLUMN_NAME) val original: String,
    @ColumnInfo(SMALL_COLUMN_NAME) val small: String,
) {

    public data class PictureWithRelations(
        @Embedded
        val picture: PictureLocalModel,
        @Relation(
            parentColumn = PICTURE_KEY_COLUMN_NAME,
            entityColumn = TAG_KEY_COLUMN_NAME,
            associateBy = Junction(
                value = PictureTagCrossRef::class,
                parentColumn = PictureTagCrossRef.Companion.PICTURE_KEY_X_REF_COLUMN_NAME,
                entityColumn = TAG_KEY_X_REF_COLUMN_NAME,
            )
        )
        val tags: List<TagLocalModel>,
        @Relation(
            parentColumn = PICTURE_KEY_COLUMN_NAME,
            entityColumn = COLOR_KEY_COLUMN_NAME,
            associateBy = Junction(
                value = PictureColorCrossRef::class,
                parentColumn = PictureColorCrossRef.Companion.PICTURE_KEY_X_REF_COLUMN_NAME,
                entityColumn = COLOR_KEY_X_REF_COLUMN_NAME
            )
        )
        val colors: List<ColorLocalModel>,
    )

    public companion object {
        public const val PICTURES_TABLE_NAME: String = "pictures"
        public const val PICTURE_KEY_COLUMN_NAME: String = "picture_key"
        public const val URL_COLUMN_NAME: String = "picture_url"
        public const val SHORT_URL_COLUMN_NAME: String = "picture_short_url"
        public const val VIEWS_COLUMN_NAME: String = "picture_views"
        public const val FAVORITES_COLUMN_NAME: String = "picture_favorites"
        public const val SOURCE_COLUMN_NAME: String = "picture_source"
        public const val PURITIES_COLUMN_NAME: String = "picture_purities"
        public const val CATEGORIES_COLUMN_NAME: String = "picture_categories"
        public const val DIMENSION_X_COLUMN_NAME: String = "picture_dimension_x"
        public const val DIMENSION_Y_COLUMN_NAME: String = "picture_dimension_y"
        public const val RESOLUTION_COLUMN_NAME: String = "picture_resolution"
        public const val RATIO_COLUMN_NAME: String = "picture_ratio"
        public const val FILE_SIZE_COLUMN_NAME: String = "picture_file_size"
        public const val FILE_TYPE_COLUMN_NAME: String = "picture_file_type"
        public const val CREATE_AT_COLUMN_NAME: String = "picture_create_at"
        public const val PATH_COLUMN_NAME: String = "picture_path"
        public const val LARGE_COLUMN_NAME: String = "picture_path_large"
        public const val ORIGINAL_COLUMN_NAME: String = "picture_path_original"
        public const val SMALL_COLUMN_NAME: String = "picture_path_small"
    }
}

internal fun Picture.toPictureLocalModel(): PictureLocalModel {
    return PictureLocalModel(
        key = this.key,
        url = this.url,
        shortUrl = this.shortUrl,
        views = this.views,
        favorites = this.favorites,
        source = this.source,
        purity = this.purity,
        categories = this.categories,
        dimensionX = this.dimensionX,
        dimensionY = this.dimensionY,
        resolution = this.resolution,
        ratio = this.ratio,
        fileSize = this.fileSize,
        fileType = this.fileType,
        createAt = this.createAt,
        path = this.path,
        large = this.large,
        original = this.original,
        small = this.small,
    )
}

@JvmName("FromPictureLocalModelToPicture")
internal fun PictureLocalModel.toPicture(): Picture {
    return Picture(
        key = this.key,
        url = this.url,
        shortUrl = this.shortUrl,
        views = this.views,
        favorites = this.favorites,
        source = this.source,
        purity = this.purity,
        categories = this.categories,
        dimensionX = this.dimensionX,
        dimensionY = this.dimensionY,
        resolution = this.resolution,
        ratio = this.ratio,
        fileSize = this.fileSize,
        fileType = this.fileType,
        createAt = this.createAt,
        path = this.path,
        large = this.large,
        original = this.original,
        small = this.small,
        tags = emptyList(),
        colors = emptyList(),
    )
}

internal fun Picture.toPictureLocalModelPictureWithRelations(): PictureLocalModel.PictureWithRelations {
    return PictureLocalModel.PictureWithRelations(
        picture = PictureLocalModel(
            key = this.key,
            url = this.url,
            shortUrl = this.shortUrl,
            views = this.views,
            favorites = this.favorites,
            source = this.source,
            purity = this.purity,
            categories = this.categories,
            dimensionX = this.dimensionX,
            dimensionY = this.dimensionY,
            resolution = this.resolution,
            ratio = this.ratio,
            fileSize = this.fileSize,
            fileType = this.fileType,
            createAt = this.createAt,
            path = this.path,
            large = this.large,
            original = this.original,
            small = this.small,
        ),
        tags = this.tags.toTagLocalModel(),
        colors = this.colors.toColorLocalModel(),
    )
}

internal fun createPictureWithRelation(
    picture: Picture,
    tags: List<Tag>,
    colors: List<Color>
): PictureLocalModel.PictureWithRelations {
    return PictureLocalModel.PictureWithRelations(
        picture = PictureLocalModel(
            key = picture.key,
            url = picture.url,
            shortUrl = picture.shortUrl,
            views = picture.views,
            favorites = picture.favorites,
            source = picture.source,
            purity = picture.purity,
            categories = picture.categories,
            dimensionX = picture.dimensionX,
            dimensionY = picture.dimensionY,
            resolution = picture.resolution,
            ratio = picture.ratio,
            fileSize = picture.fileSize,
            fileType = picture.fileType,
            createAt = picture.createAt,
            path = picture.path,
            large = picture.large,
            original = picture.original,
            small = picture.small,
        ),
        tags = tags.toTagLocalModel(),
        colors = colors.toColorLocalModel(),
    )
}

internal fun PictureLocalModel.PictureWithRelations.toPicture(): Picture {
    return Picture(
        key = this.picture.key,
        url = this.picture.url,
        shortUrl = this.picture.shortUrl,
        views = this.picture.views,
        favorites = this.picture.favorites,
        source = this.picture.source,
        purity = this.picture.purity,
        categories = this.picture.categories,
        dimensionX = this.picture.dimensionX,
        dimensionY = this.picture.dimensionY,
        resolution = this.picture.resolution,
        ratio = this.picture.ratio,
        fileSize = this.picture.fileSize,
        fileType = this.picture.fileType,
        createAt = this.picture.createAt,
        path = this.picture.path,
        large = this.picture.large,
        original = this.picture.original,
        small = this.picture.small,
        tags = this.tags.toTag(),
        colors = this.colors.toColor(),
    )
}