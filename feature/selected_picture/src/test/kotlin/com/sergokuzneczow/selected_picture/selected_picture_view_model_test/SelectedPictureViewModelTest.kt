package com.sergokuzneczow.selected_picture.selected_picture_view_model_test

import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelations2UseCase
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelations2UseCase.Answer
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelations2UseCase.Answer.AnswerState
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PictureRepositoryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class SelectedPictureViewModelTest {

    private val fakeGetPictureWithRelations2Case: FakeGetPictureWithRelations2UseCase = FakeGetPictureWithRelations2UseCase()

    @Before
    fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    fun `should handle both success and error scenarios`() {
        val cachedAnswer: Answer = createAnswer(pictureKey = "cached", answerState = AnswerState.CACHED)
        val updatedAnswer: Answer = createAnswer(pictureKey = "updated", answerState = AnswerState.UPDATED)


    }
}

internal fun createAnswer(pictureKey: String, answerState: AnswerState): Answer {
    return Answer(
        data = PictureWithRelations(
            picture = mock<Picture>().apply { `when`(this.key).thenReturn(pictureKey) },
            tags = emptyList(),
            colors = emptyList(),
        ),
        state = answerState,
    )
}

internal class FakeGetPictureWithRelations2UseCase(
    private val pictureRepository: PictureRepositoryApi = object : PictureRepositoryApi {
        override fun getPicture(pictureKey: String): Flow<List<Picture>> {
            TODO("Not yet implemented")
        }

        override fun getPictureWithRelation(pictureKey: String): Flow<PictureWithRelations?> {
            TODO("Not yet implemented")
        }

        override suspend fun getCachedPictureWithRelation(pictureKey: String): PictureWithRelations? {
            TODO("Not yet implemented")
        }

        override suspend fun getActualPictureWithRelation(pictureKey: String): PictureWithRelations {
            TODO("Not yet implemented")
        }

        override suspend fun cachingPictureWithRelation(pictureWithRelations: PictureWithRelations) {
            TODO("Not yet implemented")
        }

        override suspend fun updatePicture(pictureKey: String) {
            TODO("Not yet implemented")
        }

        override suspend fun updatePictureWithRelation(pictureKey: String) {
            TODO("Not yet implemented")
        }
    },
) : GetPictureWithRelations2UseCase(pictureRepository) {

    internal val dataSource: MutableSharedFlow<Result<Answer>> = MutableSharedFlow()

    override fun execute(scope: CoroutineScope, pictureKey: String): Flow<Result<Answer>> = dataSource

    internal fun emitData(scope: CoroutineScope, data: Result<Answer>) {
        scope.launch { dataSource.emit(data) }
    }
}