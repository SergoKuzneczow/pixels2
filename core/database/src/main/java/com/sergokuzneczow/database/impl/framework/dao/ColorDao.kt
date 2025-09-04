package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sergokuzneczow.database.impl.framework.entities.ColorLocalModel

@Dao
public interface ColorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public fun insertOrIgnoreColor(value: ColorLocalModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public fun insertOrIgnoreColors(values: List<ColorLocalModel>)

    @Query(
        "select ${ColorLocalModel.COLOR_KEY_COLUMN_NAME} " +
                "from ${ColorLocalModel.COLORS_TABLE_NAME} " +
                "where ${ColorLocalModel.COLOR_NAME_COLUMN_NAME}=:name"
    )
    public fun getColorKeyByColorName(name: String): String

    @Query(
        "select ${ColorLocalModel.COLOR_KEY_COLUMN_NAME} " +
                "from ${ColorLocalModel.COLORS_TABLE_NAME} " +
                "where ${ColorLocalModel.COLOR_NAME_COLUMN_NAME} in (:names)"
    )
    public fun getColorsKeysByColorsNames(names: List<String>): List<String>

    @Transaction
    public fun insertOrIgnoreColorReturnColorKey(color: ColorLocalModel): String {
        insertOrIgnoreColor(color)
        return getColorKeyByColorName(color.name)
    }

    public fun insertOrIgnoreColorReturnColorKeys(colors: List<ColorLocalModel>): List<String> {
        insertOrIgnoreColors(colors)
        val colorsNames: List<String> = colors.map { it.name }
        return getColorsKeysByColorsNames(colorsNames)
    }
}