package com.sergokuzneczow.database.impl.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergokuzneczow.database.impl.framework.dao.PageDao
import com.sergokuzneczow.database.impl.framework.dao.PictureDao
import com.sergokuzneczow.database.impl.framework.dao.PixelsSettingsDao
import com.sergokuzneczow.database.impl.framework.dao.TagDao
import com.sergokuzneczow.database.impl.framework.entities.ColorLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef
import com.sergokuzneczow.database.impl.framework.entities.PictureColorCrossRef
import com.sergokuzneczow.database.impl.framework.entities.PictureLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PictureTagCrossRef
import com.sergokuzneczow.database.impl.framework.entities.SettingsLocalModel
import com.sergokuzneczow.database.impl.framework.entities.TagLocalModel

@Database(
    entities = [
        PictureLocalModel::class,
        PageLocalModel::class,
        TagLocalModel::class,
        ColorLocalModel::class,
        SettingsLocalModel::class,
        PagePictureCrossRef::class,
        PictureTagCrossRef::class,
        PictureColorCrossRef::class,
    ],
    version = 3,
    exportSchema = false,
)
internal abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getPageDao(): PageDao

    abstract fun getPictureDao(): PictureDao

    abstract fun getTagDao(): TagDao

    abstract fun getSettingsDao(): PixelsSettingsDao
}