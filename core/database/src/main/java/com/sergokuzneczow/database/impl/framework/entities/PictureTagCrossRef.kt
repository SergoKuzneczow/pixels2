package com.sergokuzneczow.database.impl.framework.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.sergokuzneczow.database.impl.framework.entities.PictureTagCrossRef.Companion.PICTURE_AND_TAG_X_REF_TABLE_NAME
import com.sergokuzneczow.database.impl.framework.entities.PictureTagCrossRef.Companion.PICTURE_KEY_X_REF_COLUMN_NAME
import com.sergokuzneczow.database.impl.framework.entities.PictureTagCrossRef.Companion.TAG_KEY_X_REF_COLUMN_NAME

@Entity(
    tableName = PICTURE_AND_TAG_X_REF_TABLE_NAME,
    primaryKeys = [PICTURE_KEY_X_REF_COLUMN_NAME, TAG_KEY_X_REF_COLUMN_NAME],
)
public data class PictureTagCrossRef(
    @ColumnInfo(name = PICTURE_KEY_X_REF_COLUMN_NAME) val pictureKey: String,
    @ColumnInfo(name = TAG_KEY_X_REF_COLUMN_NAME) val tagKey: Int,
) {

    public companion object {

        public const val PICTURE_AND_TAG_X_REF_TABLE_NAME: String = "picture_and_tag_cross_ref"
        public const val PICTURE_KEY_X_REF_COLUMN_NAME: String = "picture_key_x_ref"
        public const val TAG_KEY_X_REF_COLUMN_NAME: String = "tag_key_x_ref"
    }
}