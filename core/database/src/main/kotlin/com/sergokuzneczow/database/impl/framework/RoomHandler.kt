package com.sergokuzneczow.database.impl.framework

import android.content.Context
import androidx.room.Room
import com.sergokuzneczow.database.impl.framework.dao.PageDao
import com.sergokuzneczow.database.impl.framework.dao.PictureDao
import com.sergokuzneczow.database.impl.framework.dao.PixelsSettingsDao
import com.sergokuzneczow.database.impl.framework.dao.TagDao
import com.sergokuzneczow.database.impl.framework.database.ApplicationDatabase

internal class RoomHandler(context: Context) {

    private var room: ApplicationDatabase = Room.databaseBuilder(context, ApplicationDatabase::class.java, "database").build()

    private val pictureDao: PictureDao by lazy { room.getPictureDao() }

    private val pageDao: PageDao by lazy { room.getPageDao() }

    private val tagDao: TagDao by lazy { room.getTagDao() }

    private val settingsDao: PixelsSettingsDao by lazy { room.getSettingsDao() }

    internal fun providePictureDao(): PictureDao = pictureDao

    internal fun providePageDao(): PageDao = pageDao

    internal fun provideTagDao(): TagDao = tagDao

    internal fun provideSettingsDao(): PixelsSettingsDao = settingsDao
}