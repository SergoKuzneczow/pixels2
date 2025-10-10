package com.sergokuzneczow.domain.get_picture_with_relations_case

import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PictureRepositoryApi
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

public class GetPictureWithRelations2Case @Inject constructor(
    private val pictureRepository: PictureRepositoryApi,
) {

    private companion object {
        private const val RESYNC_DELAY: Long = 3_000
    }

    private val resyncListener: MutableSharedFlow<Unit> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    public fun execute(scope: CoroutineScope, pictureKey: String): Flow<Result<Answer>> {
        return flow {
            try {
                val cached: PictureWithRelations? = pictureRepository.getCachedPictureWithRelation(pictureKey)
                cached?.let { emit(Result.success(Answer(it, Answer.AnswerState.CACHED))) }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }

            resyncListener.collect {
                println("resync")
                //if (it) delay(RESYNC_DELAY)
                try {
                    val actual: PictureWithRelations = pictureRepository.getActualPictureWithRelation(pictureKey)
                    this.emit(Result.success(Answer(actual, Answer.AnswerState.UPDATED)))
                    pictureRepository.cachingPictureWithRelation(actual)
                } catch (e: Exception) {
                    emit(Result.failure(e))
                    scope.launch { resyncListener.emit(Unit) }
                }
            }
        }.onStart {
            scope.launch { resyncListener.emit(Unit) }
        }
            .catch {
                println("catch")
//            emit(Result.failure(it))
//            scope.launch { resyncListener.emit(Unit) }
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
}