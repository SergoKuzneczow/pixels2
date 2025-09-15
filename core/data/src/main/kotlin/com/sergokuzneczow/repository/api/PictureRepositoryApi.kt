package com.sergokuzneczow.repository.api

import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import kotlinx.coroutines.flow.Flow

public interface PictureRepositoryApi {

    public fun getPicture(pictureKey: String): Flow<List<Picture>>

    public fun getPictureWithRelation(pictureKey: String): Flow<PictureWithRelations?>

    public suspend fun getActualPictureWithRelation(pictureKey: String): PictureWithRelations

    public suspend fun updatePicture(pictureKey: String)

    public suspend fun updatePictureWithRelation(pictureKey: String)
}