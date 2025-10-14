package com.sergokuzneczow.domain.get_picture_with_relations_case

import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelations2UseCase.ResyncStrategy.INSTANTLY
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelations2UseCase.ResyncStrategy.WITH_DELAY
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PictureRepositoryApi
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

public open class GetPictureWithRelations2UseCase @Inject constructor(
    private val pictureRepository: PictureRepositoryApi,
) {

    private companion object {
        private const val RESYNC_DELAY: Long = 3_000
    }

    private val resyncListener: MutableSharedFlow<ResyncStrategy> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    public open fun execute(scope: CoroutineScope, pictureKey: String): Flow<Result<Answer>> {
        return flow {

            try {
                val cached: PictureWithRelations? = pictureRepository.getCachedPictureWithRelation(pictureKey)
                cached?.let { emit(Result.success(Answer(it, Answer.AnswerState.CACHED))) }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }

            resyncListener.collect {
                if (it == WITH_DELAY) delay(RESYNC_DELAY)
                try {
                    val actual: PictureWithRelations = pictureRepository.getActualPictureWithRelation(pictureKey)
                    this.emit(Result.success(Answer(actual, Answer.AnswerState.UPDATED)))
                    runCatching { pictureRepository.cachingPictureWithRelation(actual) }
                } catch (e: Exception) {
                    emit(Result.failure(e))
                    scope.launch { resyncListener.emit(WITH_DELAY) }
                }
            }
        }.onStart {
            scope.launch { resyncListener.emit(INSTANTLY) }
        }
    }

    public data class Answer(
        val data: PictureWithRelations,
        val state: AnswerState
    ) {
        public enum class AnswerState {
            CACHED,
            UPDATED,
        }
    }

    private enum class ResyncStrategy {
        INSTANTLY,
        WITH_DELAY,
    }
}