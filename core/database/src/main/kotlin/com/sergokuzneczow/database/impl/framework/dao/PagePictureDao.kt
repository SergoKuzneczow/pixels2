package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef

@Dao
public interface PagePictureDao : PictureDao {

    @Query(
        "delete from ${PagePictureCrossRef.PAGE_PICTURE_X_REF_TABLE_NAME} " +
                "where ${PagePictureCrossRef.PAGE_KEY_X_REF_COLUMN_NAME}=:pageKey"
    )
    public fun deletePagePictureCrossRefByPageKey(pageKey: Long)

    @Query(
        "delete from ${PagePictureCrossRef.PAGE_PICTURE_X_REF_TABLE_NAME} " +
                "where ${PagePictureCrossRef.PICTURE_KEY_X_REF_COLUMN_NAME}=:pictureKey"
    )
    public fun deletePagePictureCrossRefByPictureKey(pictureKey: String)

    @Query(
        "delete from ${PagePictureCrossRef.PAGE_PICTURE_X_REF_TABLE_NAME} " +
                "where ${PagePictureCrossRef.PAGE_KEY_X_REF_COLUMN_NAME} in (:pageKeys) " +
                "and ${PagePictureCrossRef.PICTURE_KEY_X_REF_COLUMN_NAME} in (:pictureKeys)"
    )
    public fun deletePagePictureCrossRefByPageAndPictureKeys(pageKeys: List<Long>, pictureKeys: List<String>)

    @Insert(onConflict = IGNORE)
    public fun insertOrIgnorePageAndPictureCrossRef(columns: List<PagePictureCrossRef>)

    @Query(
        "select ${PagePictureCrossRef.PICTURE_KEY_X_REF_COLUMN_NAME} " +
                "from ${PagePictureCrossRef.PAGE_PICTURE_X_REF_TABLE_NAME} " +
                "where ${PagePictureCrossRef.PAGE_KEY_X_REF_COLUMN_NAME}=:pageKey " +
                "order by ${PagePictureCrossRef.POSITION_X_REF_COLUMN_NAME} asc"
    )
    public fun getPictureKeysSortedByPosition(pageKey: Long): List<String>
}