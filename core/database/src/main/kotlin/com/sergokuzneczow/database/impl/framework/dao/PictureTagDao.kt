package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sergokuzneczow.database.impl.framework.entities.PictureTagCrossRef
import com.sergokuzneczow.database.impl.framework.entities.TagLocalModel

@Dao
public interface PictureTagDao : TagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public fun insertOrIgnorePictureAndTagCrossRef(items: List<PictureTagCrossRef>)

    @Query(
        "delete from ${PictureTagCrossRef.PICTURE_AND_TAG_X_REF_TABLE_NAME} " +
                "where ${PictureTagCrossRef.PICTURE_KEY_X_REF_COLUMN_NAME}=:pictureKey"
    )
    public fun deletePictureAndTagCrossByPictureKey(pictureKey: String)

    @Transaction
    public fun insertOrIgnorePictureAndTags(pictureKey: String, tags: List<TagLocalModel>) {
        deletePictureAndTagCrossByPictureKey(pictureKey)
        val tagKeys: List<Int> = insertOrIgnoreTagsReturnTagsKeys(tags)
        val pictureTagCrossRefs: List<PictureTagCrossRef> = tagKeys.map { tagKey: Int ->
            PictureTagCrossRef(
                pictureKey = pictureKey,
                tagKey = tagKey,
            )
        }
        insertOrIgnorePictureAndTagCrossRef(pictureTagCrossRefs)
    }
}