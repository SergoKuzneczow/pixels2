package com.sergokuzneczow.database.impl.framework.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.sergokuzneczow.database.impl.framework.entities.PictureColorCrossRef.Companion.COLOR_KEY_X_REF_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PictureColorCrossRef.Companion.PICTURE_AND_COLOR_X_REF_TABLE_NAME
import com.sergokuzneczow.database.impl.framework.entities.PictureColorCrossRef.Companion.PICTURE_KEY_X_REF_COLUMN_NAME

@Entity(
    tableName = PICTURE_AND_COLOR_X_REF_TABLE_NAME,
    primaryKeys = [PICTURE_KEY_X_REF_COLUMN_NAME, COLOR_KEY_X_REF_COLUMN_NAME],
)
public data class PictureColorCrossRef(
    @ColumnInfo(name = PICTURE_KEY_X_REF_COLUMN_NAME) val pictureKey: String,
    @ColumnInfo(name = COLOR_KEY_X_REF_COLUMN_NAME) val colorKey: String,
){

    public companion object {

        public const val PICTURE_AND_COLOR_X_REF_TABLE_NAME: String = "picture_and_color_cross_ref"
        public const val PICTURE_KEY_X_REF_COLUMN_NAME: String = "picture_and_color_picture_key_x_ref"
        public const val COLOR_KEY_X_REF_COLUMN_NAME: String = "picture_and_color_color_key_x_ref"
    }
}