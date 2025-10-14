package com.sergokuzneczow.domain.get_picture_with_relations_2_use_case_test

import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PictureRepositoryApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakePictureRepositoryApi : PictureRepositoryApi {

    internal companion object {
        internal const val CACHED_EXCEPTION_MESSAGE = "Cached data source exception"
        internal const val ACTUAL_EXCEPTION_MESSAGE = "Actual data source exception"
    }

    private lateinit var actualData: PictureWithRelations

    private lateinit var fakeLocalDataSource: MutableStateFlow<PictureWithRelations?>

    private var cachedRequestDelay: Long? = null

    private var actualRequestDelay: Long? = null

    private var cachedRequestThrowCounter: Int = 0

    private var actualRequestThrowCounter: Int = 0

    override fun getPicture(pictureKey: String): Flow<List<Picture>> = TODO("Not yet implemented")

    override fun getPictureWithRelation(pictureKey: String): Flow<PictureWithRelations?> = TODO("Not yet implemented")

    override suspend fun getCachedPictureWithRelation(pictureKey: String): PictureWithRelations? {
        cachedRequestDelay?.let { delay(it) }
        if (cachedRequestThrowCounter > 0) {
            cachedRequestThrowCounter--
            throw IllegalStateException(CACHED_EXCEPTION_MESSAGE)
        }
        return fakeLocalDataSource.value
    }

    override suspend fun getActualPictureWithRelation(pictureKey: String): PictureWithRelations {
        actualRequestDelay?.let { delay(it) }
        if (actualRequestThrowCounter > 0) {
            actualRequestThrowCounter--
            throw IllegalStateException(ACTUAL_EXCEPTION_MESSAGE)
        }
        return actualData
    }

    override suspend fun cachingPictureWithRelation(pictureWithRelations: PictureWithRelations) {
        fakeLocalDataSource.emit(pictureWithRelations)
    }

    override suspend fun updatePicture(pictureKey: String) = TODO("Not yet implemented")

    override suspend fun updatePictureWithRelation(pictureKey: String) = TODO("Not yet implemented")

    fun setFakePictureRepositoryApiState(
        cachedData: PictureWithRelations?,
        actualData: PictureWithRelations,
        cachedRequestDelay: Long? = null,
        actualRequestDelay: Long? = null,
        cachedRequestThrowCounter: Int = 0,
        actualRequestThrowCounter: Int = 0,
    ) {
        this.fakeLocalDataSource = MutableStateFlow(cachedData)
        this.actualData = actualData
        this.cachedRequestDelay = cachedRequestDelay
        this.actualRequestDelay = actualRequestDelay
        this.cachedRequestThrowCounter = cachedRequestThrowCounter
        this.actualRequestThrowCounter = actualRequestThrowCounter
    }
}