package com.sergokuzneczow.repository.impl

import com.sergokuzneczow.database.api.PixelsDatabaseDataSourceApi
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.network.api.PixelsNetworkDataSourceApi
import com.sergokuzneczow.repository.api.PictureRepositoryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class PictureRepositoryImpl @Inject constructor(
    private val databaseApi: PixelsDatabaseDataSourceApi,
    private val networkApi: PixelsNetworkDataSourceApi,
) : PictureRepositoryApi {

    override fun getPicture(pictureKey: String): Flow<List<Picture>> = databaseApi.getPictureByKey(pictureKey)

    override fun getPictureWithRelation(pictureKey: String): Flow<PictureWithRelations?> =
        databaseApi.getPictureWithRelationByKeyAsFlow(pictureKey).map { it.firstOrNull() }

    override suspend fun getCachedPictureWithRelation(pictureKey: String): PictureWithRelations? = databaseApi.getPictureWithRelationByKey(pictureKey)

    override suspend fun getActualPictureWithRelation(pictureKey: String): PictureWithRelations = networkApi.getPicture(pictureKey)

    override suspend fun cachingPictureWithRelation(pictureWithRelations: PictureWithRelations) {
        databaseApi.setPicture(pictureWithRelations)
    }

    override suspend fun updatePicture(pictureKey: String) {
        val actualPicture: PictureWithRelations = networkApi.getPicture(pictureKey)
        databaseApi.setPicture(actualPicture.picture)
    }

    override suspend fun updatePictureWithRelation(pictureKey: String) {
        val actualPicture: PictureWithRelations = networkApi.getPicture(pictureKey)
        databaseApi.setPicture(actualPicture)
    }
}