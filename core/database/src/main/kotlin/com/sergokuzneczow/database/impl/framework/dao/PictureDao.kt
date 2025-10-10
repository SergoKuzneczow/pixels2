package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.sergokuzneczow.database.impl.framework.entities.PictureLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
public interface PictureDao : PictureTagDao, PictureColorDao {

    @Insert(onConflict = IGNORE)
    public suspend fun insertOrIgnorePicture(column: PictureLocalModel)

    @Insert(onConflict = IGNORE)
    public suspend fun insertOrIgnorePictures(columns: List<PictureLocalModel>)

    @Query(
        "select * " +
                "from ${PictureLocalModel.PICTURES_TABLE_NAME} " +
                "where ${PictureLocalModel.PICTURE_KEY_COLUMN_NAME}=:key"
    )
    public suspend fun getPictureByKey(key: String): List<PictureLocalModel>

    @Query(
        "select * " +
                "from ${PictureLocalModel.PICTURES_TABLE_NAME} " +
                "where ${PictureLocalModel.PICTURE_KEY_COLUMN_NAME} in (:key)"
    )
    public suspend fun getPictureByKeys(key: List<String>): List<PictureLocalModel>

    @Query(
        "select * " +
                "from ${PictureLocalModel.PICTURES_TABLE_NAME} " +
                "where ${PictureLocalModel.PICTURE_KEY_COLUMN_NAME}=:key"
    )
    public fun getPictureByKeyAsFlow(key: String): Flow<List<PictureLocalModel>>

    @Query(
        "select * " +
                "from ${PictureLocalModel.PICTURES_TABLE_NAME} " +
                "where ${PictureLocalModel.PICTURE_KEY_COLUMN_NAME} in (:keys)"
    )
    public fun getPictureByKeysAsFlow(keys: List<String>): Flow<List<PictureLocalModel>>

    @Query(
        "select ${PictureLocalModel.PICTURE_KEY_COLUMN_NAME} " +
                "from ${PictureLocalModel.PICTURES_TABLE_NAME} " +
                "where ${PictureLocalModel.URL_COLUMN_NAME}=:url "
    )
    public suspend fun getPictureKeyByUrlAndPath(url: String): String?

    @Transaction
    @Query(
        "select * " +
                "from ${PictureLocalModel.PICTURES_TABLE_NAME} " +
                "where ${PictureLocalModel.PICTURE_KEY_COLUMN_NAME}=:key"
    )
    public fun getPictureWithRelationsByKey(key: String): PictureLocalModel.PictureWithRelations?

    @Transaction
    @Query(
        "select * " +
                "from ${PictureLocalModel.PICTURES_TABLE_NAME} " +
                "where ${PictureLocalModel.PICTURE_KEY_COLUMN_NAME}=:key"
    )
    public fun getPictureWithRelationsByKeyAsFlow(key: String): Flow<List<PictureLocalModel.PictureWithRelations>>

    @Transaction
    public suspend fun insertOrIgnorePictureReturnKey(picture: PictureLocalModel): String? {
        insertOrIgnorePicture(picture)
        return getPictureKeyByUrlAndPath(picture.url)
    }

    @Transaction
    public suspend fun insertOrIgnorePictureWithRelationsReturnPictureKey(pictureWithRelations: PictureLocalModel.PictureWithRelations): String? {
        val pictureKey: String? = insertOrIgnorePictureReturnKey(pictureWithRelations.picture)
        if (pictureKey != null) {
            insertOrIgnorePictureAndTags(pictureKey, pictureWithRelations.tags)
            insertOrIgnorePictureAndColors(pictureKey, pictureWithRelations.colors)
        }
        return pictureKey
    }

    @Transaction
    public suspend fun insertOrIgnorePictureWithRelationsReturnPictureKey(pictureWithRelations: List<PictureLocalModel.PictureWithRelations>): List<String> {
        val picturesKeys: MutableList<String> = mutableListOf()
        pictureWithRelations.forEach {
            val pictureKey = insertOrIgnorePictureWithRelationsReturnPictureKey(it)
            if (pictureKey != null) picturesKeys.add(pictureKey)
        }
        return picturesKeys
    }
}