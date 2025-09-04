package com.sergokuzneczow.database.impl.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergokuzneczow.database.impl.framework.dao.PageDao
import com.sergokuzneczow.database.impl.framework.dao.PictureDao
import com.sergokuzneczow.database.impl.framework.dao.TagDao
import com.sergokuzneczow.database.impl.framework.entities.ColorLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef
import com.sergokuzneczow.database.impl.framework.entities.PictureColorCrossRef
import com.sergokuzneczow.database.impl.framework.entities.PictureLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PictureTagCrossRef
import com.sergokuzneczow.database.impl.framework.entities.TagLocalModel

@Database(
    entities = [
        PictureLocalModel::class,
        PageLocalModel::class,
        TagLocalModel::class,
        ColorLocalModel::class,
        PagePictureCrossRef::class,
        PictureTagCrossRef::class,
        PictureColorCrossRef::class,
    ],
    version = 1,
    exportSchema = false
)
public abstract class ApplicationDatabase : RoomDatabase() {

    public abstract fun getPageDao(): PageDao

    public abstract fun getPictureDao(): PictureDao

    public abstract fun getTagDao(): TagDao
}