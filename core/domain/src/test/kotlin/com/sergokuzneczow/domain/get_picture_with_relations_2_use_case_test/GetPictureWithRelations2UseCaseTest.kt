package com.sergokuzneczow.domain.get_picture_with_relations_2_use_case_test

import app.cash.turbine.test
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.impl.picture_repository_impl.FakePictureRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class GetPictureWithRelations2UseCaseTest {

    private val fakePictureRepositoryImpl = FakePictureRepositoryImpl()

    private val getPictureWithRelations2UseCase = GetPictureWithRelations2UseCase(fakePictureRepositoryImpl)

    @Before
    fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun `is there is cached data, it should be processed before the actual data`(): TestResult = runTest {
        val cached: PictureWithRelations = createPictureWithRelations("cached")
        val actual: PictureWithRelations = createPictureWithRelations("actual")

        fakePictureRepositoryImpl.setFakePictureRepositoryApiState(
            cachedData = cached,
            actualData = actual,
        )

        getPictureWithRelations2UseCase.execute(backgroundScope, "anyKey").test {
            assertEquals("cached", awaitItem().getOrNull()?.data?.picture?.key)
            assertEquals("actual", awaitItem().getOrNull()?.data?.picture?.key)
        }
    }

    @Test
    fun `cached data should be skipped, if there isn't`(): TestResult = runTest {
        val actual: PictureWithRelations = createPictureWithRelations("actual")

        fakePictureRepositoryImpl.setFakePictureRepositoryApiState(
            cachedData = null,
            actualData = actual,
        )

        getPictureWithRelations2UseCase.execute(backgroundScope, "anyKey").test {
            assertEquals("actual", awaitItem().getOrNull()?.data?.picture?.key)
        }
    }

    @Test
    fun `repeat the request for new data if the first attempt fails`(): TestResult = runTest {
        val actual: PictureWithRelations = createPictureWithRelations("actual")

        fakePictureRepositoryImpl.setFakePictureRepositoryApiState(
            cachedData = null,
            actualData = actual,
            actualRequestThrowCounter = 1,
        )

        getPictureWithRelations2UseCase.execute(backgroundScope, "anyKey").test {
            assertEquals(FakePictureRepositoryImpl.ACTUAL_EXCEPTION_MESSAGE, awaitItem().exceptionOrNull()?.message)
            assertEquals("actual", awaitItem().getOrNull()?.data?.picture?.key)
        }
    }

    @Test
    fun `retry requesting new data if the first attempt fails after receiving cached data`(): TestResult = runTest {
        val cached: PictureWithRelations = createPictureWithRelations("cached")
        val actual: PictureWithRelations = createPictureWithRelations("actual")

        fakePictureRepositoryImpl.setFakePictureRepositoryApiState(
            cachedData = cached,
            actualData = actual,
            actualRequestThrowCounter = 1,
        )

        getPictureWithRelations2UseCase.execute(backgroundScope, "anyKey").test {
            assertEquals("cached", awaitItem().getOrNull()?.data?.picture?.key)
            assertEquals(FakePictureRepositoryImpl.ACTUAL_EXCEPTION_MESSAGE, awaitItem().exceptionOrNull()?.message)
            assertEquals("actual", awaitItem().getOrNull()?.data?.picture?.key)
        }
    }

    private fun createPictureWithRelations(pictureKey: String): PictureWithRelations = PictureWithRelations(
        picture = mock<Picture>().apply { `when`(this.key).thenReturn(pictureKey) },
        tags = emptyList(),
        colors = emptyList(),
    )
}