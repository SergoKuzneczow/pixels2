package com.sergokuzneczow.database.impl.framework.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.sergokuzneczow.models.Tag
import com.sergokuzneczow.database.impl.framework.entities.TagLocalModel.Companion.TAGS_TABLE_NAME
import com.sergokuzneczow.database.impl.framework.entities.TagLocalModel.Companion.TAG_KEY_COLUMN_NAME

@Entity(
    tableName = TAGS_TABLE_NAME,
    primaryKeys = [TAG_KEY_COLUMN_NAME],
)
public data class TagLocalModel(
    @ColumnInfo(name = TAG_KEY_COLUMN_NAME) public val id: Int,
    @ColumnInfo(name = TAG_NAME_COLUMN_NAME) public val name: String,
    @ColumnInfo(name = TAG_ALIAS_COLUMN_NAME) public val alias: String,
    @ColumnInfo(name = TAG_CATEGORY_KEY_COLUMN_NAME) public val categoryId: Int,
    @ColumnInfo(name = TAG_CATEGORY_NAME_COLUMN_NAME) public val categoryName: String,
    @ColumnInfo(name = TAG_PURITY_NAME_COLUMN_NAME) public val purity: String,
    @ColumnInfo(name = TAG_CREATE_AT_COLUMN_NAME) public val createdAt: String,
) {

    public companion object {

        public const val TAGS_TABLE_NAME: String = "tags"
        public const val TAG_KEY_COLUMN_NAME: String = "tag_key"
        public const val TAG_NAME_COLUMN_NAME: String = "tag_name"
        public const val TAG_ALIAS_COLUMN_NAME: String = "tag_alias_name"
        public const val TAG_CATEGORY_KEY_COLUMN_NAME: String = "tag_category_key"
        public const val TAG_CATEGORY_NAME_COLUMN_NAME: String = "tag_category_name"
        public const val TAG_PURITY_NAME_COLUMN_NAME: String = "tag_purity_name"
        public const val TAG_CREATE_AT_COLUMN_NAME: String = "tag_create_at"
    }
}

internal fun Tag.toTagLocalModel(): TagLocalModel {
    return TagLocalModel(
        id = this.id,
        name = this.name,
        alias = this.alias,
        categoryId = this.categoryId,
        categoryName = this.categoryName,
        purity = this.purity,
        createdAt = this.createdAt,
    )
}

internal fun TagLocalModel.toTag(): Tag {
    return Tag(
        id = this.id,
        name = this.name,
        alias = this.alias,
        categoryId = this.categoryId,
        categoryName = this.categoryName,
        purity = this.purity,
        createdAt = this.createdAt,
    )
}

internal fun List<Tag>.toTagLocalModel(): List<TagLocalModel> = this.map { it.toTagLocalModel() }

internal fun List<TagLocalModel>.toTag(): List<Tag> = this.map { it.toTag() }