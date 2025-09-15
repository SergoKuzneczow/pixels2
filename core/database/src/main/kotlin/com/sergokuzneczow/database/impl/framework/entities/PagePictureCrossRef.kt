package com.sergokuzneczow.database.impl.framework.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef.Companion.PAGE_KEY_X_REF_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef.Companion.PAGE_PICTURE_X_REF_TABLE_NAME
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef.Companion.PICTURE_KEY_X_REF_COLUMN_NAME

@Entity(
    tableName = PAGE_PICTURE_X_REF_TABLE_NAME,
    primaryKeys = [PAGE_KEY_X_REF_COLUMN_NAME, PICTURE_KEY_X_REF_COLUMN_NAME],
    foreignKeys = [ForeignKey(
        entity = PageLocalModel::class,
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        parentColumns = [PageLocalModel.PAGE_KEY_COLUMN_NAME],
        childColumns = [PAGE_KEY_X_REF_COLUMN_NAME],
    )],
)
public data class PagePictureCrossRef(
    @ColumnInfo(PAGE_KEY_X_REF_COLUMN_NAME) val pageKey: Long,
    @ColumnInfo(PICTURE_KEY_X_REF_COLUMN_NAME) val pictureKey: String,
    @ColumnInfo(POSITION_X_REF_COLUMN_NAME) val position: Int = 0,
) {

    public companion object {
        public const val PAGE_PICTURE_X_REF_TABLE_NAME: String = "page_and_picture_cross_ref"
        public const val PAGE_KEY_X_REF_COLUMN_NAME: String = "page_key_x_ref"
        public const val PICTURE_KEY_X_REF_COLUMN_NAME: String = "picture_key_x_ref"
        public const val POSITION_X_REF_COLUMN_NAME: String = "positions_x_ref"
    }
}