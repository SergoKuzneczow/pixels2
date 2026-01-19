package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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

    @Transaction
    suspend fun changeThemeState(key: Long, newThemeState: String): SettingsLocalModel {
        val current = get(key) ?: throw IllegalStateException("The database must contain a row with application settings, the key of which is 0.")
        val new = current.copy(systemSettings = current.systemSettings.copy(themeState = newThemeState))
        insertOrReplace(new)
        return get(key) ?: throw IllegalStateException("The database must contain a row with application settings, the key of which is 0.")
    }
}