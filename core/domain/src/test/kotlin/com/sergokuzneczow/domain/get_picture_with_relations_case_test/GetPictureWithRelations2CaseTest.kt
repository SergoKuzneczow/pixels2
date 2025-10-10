package com.sergokuzneczow.domain.get_picture_with_relations_case_test

import app.cash.turbine.test
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelations2Case
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
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
class GetPictureWithRelations2CaseTest {

    private val fakePictureRepositoryApi = FakePictureRepositoryApi()

    private val getPictureWithRelations2Case = GetPictureWithRelations2Case(fakePictureRepositoryApi)

    @Before
    fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun `First, the cached data will be retrieved, then the actual data`(): TestResult = runTest {
        val cached = PictureWithRelations(
            picture = mock<Picture>().apply { `when`(this.key).thenReturn("cached") },
            tags = emptyList(),
            colors = emptyList(),
        )

        val actual = PictureWithRelations(
            picture = mock<Picture>().apply { `when`(this.key).thenReturn("actual") },
            tags = emptyList(),
            colors = emptyList(),
        )

        fakePictureRepositoryApi.setFakePictureRepositoryApiState(
            cachedData = cached,
            actualData = actual,
        )

        getPictureWithRelations2Case.execute(backgroundScope, "anyKey").test {
            assertEquals("cached", awaitItem().getOrNull()?.data?.picture?.key)
            assertEquals("actual", awaitItem().getOrNull()?.data?.picture?.key)
        }
    }

    @Test
    fun `First, the cached data will be retrieved, then the exception, then the actual data`(): TestResult = runTest {
        val cached = PictureWithRelations(
            picture = mock<Picture>().apply { `when`(this.key).thenReturn("cached") },
            tags = emptyList(),
            colors = emptyList(),
        )

        val actual = PictureWithRelations(
            picture = mock<Picture>().apply { `when`(this.key).thenReturn("actual") },
            tags = emptyList(),
            colors = emptyList(),
        )

        fakePictureRepositoryApi.setFakePictureRepositoryApiState(
            cachedData = cached,
            actualData = actual,
            actualRequestThrowCounter = 1,
        )

        getPictureWithRelations2Case.execute(backgroundScope, "anyKey").test {
            assertEquals("cached", awaitItem().getOrNull()?.data?.picture?.key)
            assertEquals(FakePictureRepositoryApi.ACTUAL_EXCEPTION_MESSAGE, awaitItem().exceptionOrNull()?.message)
            assertEquals("actual", awaitItem().getOrNull()?.data?.picture?.key)
        }
    }
}


private val cached = PictureWithRelations(
    picture = Picture(
        key = "cached",
        url = "",
        shortUrl = "",
        views = 0,
        favorites = 0,
        source = "",
        purity = "",
        categories = "",
        dimensionX = 0,
        dimensionY = 0,
        resolution = "",
        ratio = "",
        fileSize = 0,
        fileType = "",
        createAt = "",
        path = "",
        large = "",
        original = "",
        small = "",
    ),
    tags = emptyList(),
    colors = emptyList()
)

private val actual = PictureWithRelations(
    picture = Picture(
        key = "actual",
        url = "",
        shortUrl = "",
        views = 0,
        favorites = 0,
        source = "",
        purity = "",
        categories = "",
        dimensionX = 0,
        dimensionY = 0,
        resolution = "",
        ratio = "",
        fileSize = 0,
        fileType = "",
        createAt = "",
        path = "",
        large = "",
        original = "",
        small = "",
    ),
    tags = emptyList(),
    colors = emptyList()
)