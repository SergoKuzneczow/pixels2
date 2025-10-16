package com.sergokuzneczow.domain.get_picture_with_relations_2_use_case

import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase.Answer.AnswerState
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.models.Tag
import com.sergokuzneczow.repository.api.PictureRepositoryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

public class GetPictureWithRelations2FakeUseCase(
    pictureRepository: PictureRepositoryApi = pictureRepositoryApiMock,
) : GetPictureWithRelations2UseCase(pictureRepository) {

    private val dataSource: MutableSharedFlow<Result<Answer>> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    override fun execute(scope: CoroutineScope, pictureKey: String): Flow<Result<Answer>> = dataSource

    public suspend fun emitSuccess(
        pictureKey: String = "",
        picturePath: String = "",
        tags: List<Tag> = emptyList(),
        colors: List<Color> = emptyList(),
        answerState: AnswerState = AnswerState.CACHED,
    ) {
        dataSource.emit(
            createSuccessResult(
                pictureKey = pictureKey,
                tags = tags,
                colors = colors,
                answerState = answerState,
            )
        )
    }

    public suspend fun emitFailure(exceptionMessage: String) {
        dataSource.emit(createFailureResult(exceptionMessage))
    }

    private fun createSuccessResult(
        pictureKey: String = "",
        picturePath: String = "",
        tags: List<Tag> = emptyList(),
        colors: List<Color> = emptyList(),
        answerState: AnswerState = AnswerState.CACHED,
    ): Result<Answer> {
        return Result.success(
            Answer(
                data = PictureWithRelations(
                    picture = Picture(
                        key = pictureKey,
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
                        path = picturePath,
                        large = "",
                        original = "",
                        small = ""
                    ),
                    tags = tags,
                    colors = colors,
                ),
                state = answerState,
            )
        )
    }

    private fun createFailureResult(exceptionMessage: String): Result<Answer> {
        return Result.failure(IllegalStateException(exceptionMessage))
    }
}

private val pictureRepositoryApiMock: PictureRepositoryApi = object : PictureRepositoryApi {
    override fun getPicture(pictureKey: String): Flow<List<Picture>> = TODO("Not yet implemented")
    override fun getPictureWithRelation(pictureKey: String): Flow<PictureWithRelations?> = TODO("Not yet implemented")
    override suspend fun getCachedPictureWithRelation(pictureKey: String): PictureWithRelations? = TODO("Not yet implemented")
    override suspend fun getActualPictureWithRelation(pictureKey: String): PictureWithRelations = TODO("Not yet implemented")
    override suspend fun cachingPictureWithRelation(pictureWithRelations: PictureWithRelations) = TODO("Not yet implemented")
    override suspend fun updatePicture(pictureKey: String) = TODO("Not yet implemented")
    override suspend fun updatePictureWithRelation(pictureKey: String) = TODO("Not yet implemented")
}