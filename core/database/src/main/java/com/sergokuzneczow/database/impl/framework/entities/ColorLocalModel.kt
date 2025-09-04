package com.sergokuzneczow.database.impl.framework.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sergokuzneczow.database.impl.framework.entities.ColorLocalModel.Companion.COLORS_TABLE_NAME
import com.sergokuzneczow.database.impl.framework.entities.ColorLocalModel.Companion.COLOR_NAME_COLUMN_NAME
import com.sergokuzneczow.models.Color

@Entity(
    tableName = COLORS_TABLE_NAME,
    indices = [Index(value = [COLOR_NAME_COLUMN_NAME], unique = true)],
)
public data class ColorLocalModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(COLOR_KEY_COLUMN_NAME) val key: String,
    @ColumnInfo(COLOR_NAME_COLUMN_NAME) val name: String,
) {

    public companion object {

        public const val COLORS_TABLE_NAME: String = "colors"
        public const val COLOR_KEY_COLUMN_NAME: String = "color_key"
        public const val COLOR_NAME_COLUMN_NAME: String = "color_name"
    }
}

internal fun Color.toColorLocalModel(): ColorLocalModel {
    return ColorLocalModel(
        key = this.key,
        name = this.key,
    )
}

internal fun ColorLocalModel.toColor(): Color {
    return Color(
        key = this.key,
        name = this.key,
    )
}

internal fun List<Color>.toColorLocalModel(): List<ColorLocalModel> = this.map { it.toColorLocalModel() }

internal fun List<ColorLocalModel>.toColor(): List<Color> = this.map { it.toColor() }