package com.sergokuzneczow.database.impl.framework.impl

import com.sergokuzneczow.database.impl.framework.RoomHandler
import com.sergokuzneczow.database.impl.framework.dao.PictureDao
import com.sergokuzneczow.database.impl.framework.entities.PictureLocalModel
import kotlinx.coroutines.flow.Flow

internal class PictureLocalSource private constructor(private val pictureDao: PictureDao) {

    internal constructor(roomHandler: RoomHandler) : this(roomHandler.providePictureDao())

    internal fun getPictureByKey(pictureKey: String): Flow<List<PictureLocalModel>> = pictureDao.getPictureByKeyAsFlow(pictureKey)

    internal fun getPictureWithRelationByKeyAsFlow(pictureKey: String): Flow<List<PictureLocalModel.PictureWithRelations>> = pictureDao.getPictureWithRelationsByKeyAsFlow(pictureKey)

    internal fun getPictureWithRelationByKey(pictureKey: String): PictureLocalModel.PictureWithRelations? = pictureDao.getPictureWithRelationsByKey(pictureKey)

    internal suspend fun setPicture(picture: PictureLocalModel) {
        pictureDao.insertOrIgnorePicture(picture)
    }

    internal suspend fun setPicture(pictureWithRelations: PictureLocalModel.PictureWithRelations) {
        pictureDao.insertOrIgnorePictureWithRelationsReturnPictureKey(pictureWithRelations)
    }
}