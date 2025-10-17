package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sergokuzneczow.database.impl.framework.entities.SettingsLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PixelsSettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(column: SettingsLocalModel)

    @Query(
        "select * " +
                "from ${SettingsLocalModel.SETTINGS_TABLE_NAME} " +
                "where ${SettingsLocalModel.SETTINGS_KEY_COLUMN_NAME}=:key"
    )
    suspend fun get(key: Long): SettingsLocalModel?

    @Query(
        "select * " +
                "from ${SettingsLocalModel.SETTINGS_TABLE_NAME} " +
                "where ${SettingsLocalModel.SETTINGS_KEY_COLUMN_NAME}=:key"
    )
    fun getAsFlow(key: Long): Flow<SettingsLocalModel?>
}