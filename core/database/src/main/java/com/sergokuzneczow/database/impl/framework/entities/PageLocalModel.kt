package com.sergokuzneczow.database.impl.framework.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.Companion.CATEGORIES_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.Companion.COLOR_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.Companion.ORDER_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.Companion.PAGES_TABLE_NAME
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.Companion.PAGE_NUMBER_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.Companion.PURITIES_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.Companion.QUERY_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.Companion.SORTING_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef.Companion.PAGE_KEY_X_REF_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef.Companion.PICTURE_KEY_X_REF_COLUMN_NAME

@Entity(
    tableName = PAGES_TABLE_NAME,
    indices = [Index(
        value = [PAGE_NUMBER_COLUMN_NAME, QUERY_COLUMN_NAME, SORTING_COLUMN_NAME, ORDER_COLUMN_NAME, PURITIES_COLUMN_NAME, CATEGORIES_COLUMN_NAME, COLOR_COLUMN_NAME],
        unique = true
    )],
)
public data class PageLocalModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PAGE_KEY_COLUMN_NAME) val key: Long = 0,
    @ColumnInfo(name = LOAD_TIME_COLUMN_NAME) val loadTime: Long,
    @ColumnInfo(name = PAGE_NUMBER_COLUMN_NAME) val number: Int,
    @ColumnInfo(name = QUERY_COLUMN_NAME) val query: String,
    @ColumnInfo(name = SORTING_COLUMN_NAME) val sorting: String,
    @ColumnInfo(name = ORDER_COLUMN_NAME) val order: String,
    @ColumnInfo(name = PURITIES_COLUMN_NAME) val purities: String,
    @ColumnInfo(name = CATEGORIES_COLUMN_NAME) val categories: String,
    @ColumnInfo(name = COLOR_COLUMN_NAME) val color: String,
) {

    public data class PageWithPictures(
        @Embedded val page: PageLocalModel,
        @Relation(
            parentColumn = PAGE_KEY_COLUMN_NAME,
            entityColumn = PictureLocalModel.Companion.PICTURE_KEY_COLUMN_NAME,
            associateBy = Junction(
                PagePictureCrossRef::class,
                parentColumn = PAGE_KEY_X_REF_COLUMN_NAME,
                entityColumn = PICTURE_KEY_X_REF_COLUMN_NAME
            )
        )
        val pictures: List<PictureLocalModel>,
    )

    public data class PageWithPicturesWithRelations(
        @Embedded val page: PageLocalModel,
        @Relation(
            entity = PictureLocalModel::class,
            parentColumn = PAGE_KEY_COLUMN_NAME,
            entityColumn = PictureLocalModel.Companion.PICTURE_KEY_COLUMN_NAME,
            associateBy = Junction(
                PagePictureCrossRef::class,
                parentColumn = PAGE_KEY_X_REF_COLUMN_NAME,
                entityColumn = PICTURE_KEY_X_REF_COLUMN_NAME
            )
        )
        val picturesWithRelations: List<PictureLocalModel.PictureWithRelations>,
    )

    public companion object {

        public const val PAGES_TABLE_NAME: String = "pages"
        public const val PAGE_KEY_COLUMN_NAME: String = "page_key"
        public const val LOAD_TIME_COLUMN_NAME: String = "page_load_time"
        public const val PAGE_NUMBER_COLUMN_NAME: String = "page_number"
        public const val QUERY_COLUMN_NAME: String = "selected_query"
        public const val SORTING_COLUMN_NAME: String = "selected_sorting"
        public const val ORDER_COLUMN_NAME: String = "selected_order"
        public const val PURITIES_COLUMN_NAME: String = "selected_purities"
        public const val CATEGORIES_COLUMN_NAME: String = "selected_categories"
        public const val COLOR_COLUMN_NAME: String = "selected_color"
    }
}