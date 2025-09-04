package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sergokuzneczow.database.impl.framework.entities.ColorLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PictureColorCrossRef

@Dao
public interface PictureColorDao : ColorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public fun insertOrIgnorePictureAndColorCrossRef(items: List<PictureColorCrossRef>)

    @Query(
        "delete from ${PictureColorCrossRef.PICTURE_AND_COLOR_X_REF_TABLE_NAME} " +
                "where ${PictureColorCrossRef.PICTURE_KEY_X_REF_COLUMN_NAME}=:pictureKey"
    )
    public fun deletePictureAndColorCrossByPictureKey(pictureKey: String)

    @Transaction
    public fun insertOrIgnorePictureAndColors(pictureKey: String, colors: List<ColorLocalModel>) {
        deletePictureAndColorCrossByPictureKey(pictureKey)
        val colorsKeys: List<String> = insertOrIgnoreColorReturnColorKeys(colors)
        val pictureColorsCrossRefs: List<PictureColorCrossRef> = colorsKeys.map { colorKey ->
            PictureColorCrossRef(
                pictureKey = pictureKey,
                colorKey = colorKey,
            )
        }
        insertOrIgnorePictureAndColorCrossRef(pictureColorsCrossRefs)
    }
}