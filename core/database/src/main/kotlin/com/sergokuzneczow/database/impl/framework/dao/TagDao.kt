package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.sergokuzneczow.database.impl.framework.entities.TagLocalModel

@Dao
public interface TagDao {

    @Insert(onConflict = REPLACE)
    public fun insertOrReplaceTag(tag: TagLocalModel)

    @Insert(onConflict = IGNORE)
    public fun insertOrIgnoreTag(tag: TagLocalModel)

    @Insert(onConflict = REPLACE)
    public fun insertOrReplaceTag(tag: List<TagLocalModel>)

    @Insert(onConflict = IGNORE)
    public fun insertOrIgnoreTag(tag: List<TagLocalModel>)

    @Query(
        "select * " +
                "from ${TagLocalModel.TAGS_TABLE_NAME} " +
                "where ${TagLocalModel.TAG_KEY_COLUMN_NAME}=:key"
    )
    public fun getTag(key: Int): List<TagLocalModel>

    @Query(
        "select ${TagLocalModel.TAG_KEY_COLUMN_NAME} " +
                "from ${TagLocalModel.TAGS_TABLE_NAME} " +
                "where ${TagLocalModel.TAG_NAME_COLUMN_NAME}=:name"
    )
    public fun getTagKeyByTagName(name: String): Int?

    @Query(
        "select ${TagLocalModel.TAG_KEY_COLUMN_NAME} " +
                "from ${TagLocalModel.TAGS_TABLE_NAME} " +
                "where ${TagLocalModel.TAG_NAME_COLUMN_NAME} in (:names)"
    )
    public fun getTagsKeysByTagsNames(names: List<String>): List<Int>

    @Transaction
    public fun insertOrIgnoreTagReturnTagKey(tag: TagLocalModel): Int? {
        insertOrIgnoreTag(tag)
        return getTagKeyByTagName(tag.name)
    }

    @Transaction
    public fun insertOrIgnoreTagsReturnTagsKeys(tags: List<TagLocalModel>): List<Int> {
        insertOrIgnoreTag(tags)
        val tagsNames = tags.map { it.name }
        return getTagsKeysByTagsNames(tagsNames)
    }
}