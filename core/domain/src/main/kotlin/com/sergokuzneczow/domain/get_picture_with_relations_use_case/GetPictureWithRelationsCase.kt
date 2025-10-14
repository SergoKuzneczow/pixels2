package com.sergokuzneczow.domain.get_picture_with_relations_use_case

import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PictureRepositoryApi
import com.sergokuzneczow.utilities.logger.Level
import com.sergokuzneczow.utilities.logger.log
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

public class GetPictureWithRelationsCase @Inject constructor(
    private val pictureRepository: PictureRepositoryApi,
) {

    private companion object {
        private const val DELAY: Long = 3_000
    }

    private val dataFlow: MutableSharedFlow<Result<PictureWithRelations>> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private var notFirstSync: Boolean = false

    public fun execute(
        pictureKey: String,
        coroutineScope: CoroutineScope,
    ): SharedFlow<Result<PictureWithRelations>> {
        coroutineScope.createSourceCoroutine(pictureKey)
        coroutineScope.createSyncCoroutine(pictureKey)
        return dataFlow.asSharedFlow()
    }


    private fun CoroutineScope.createSourceCoroutine(pictureKey: String): Job = this.launch(sourceExceptionHandler(pictureKey)) {
        log(tag = "GetPictureWithRelationsCase", level = Level.INFO) { "createSourceCoroutine(); enter point; pictureKey=$pictureKey" }
        pictureRepository.getPictureWithRelation(pictureKey)
            .onEach { it?.let { itNotNull: PictureWithRelations -> dataFlow.emit(Result.success(itNotNull)) } }
            .launchIn(this)
    }

    private fun CoroutineScope.createSyncCoroutine(pictureKey: String): Job = this.launch(syncExceptionHandler(pictureKey)) {
        log(tag = "GetPictureWithRelationsCase", level = Level.INFO) { "createSyncCoroutine(); enter point; pictureKey=$pictureKey" }
        if (notFirstSync) delay(DELAY)
        else notFirstSync = true
        pictureRepository.updatePictureWithRelation(pictureKey)
    }

    private fun CoroutineScope.sourceExceptionHandler(pictureKey: String): CoroutineExceptionHandler = CoroutineExceptionHandler { c, t ->
        log(tag = "PictureProvider", level = Level.INFO) { "sourceExceptionHandler(); enter point; pictureKey=$pictureKey" }
        this.launch { dataFlow.emit(Result.failure(t)) }
        createSourceCoroutine(pictureKey)
    }

    private fun CoroutineScope.syncExceptionHandler(pictureKey: String): CoroutineExceptionHandler = CoroutineExceptionHandler { c, t ->
        log(tag = "PictureProvider", level = Level.INFO) { "syncExceptionHandler(); enter point; pictureKey=$pictureKey" }
        this.launch { dataFlow.emit(Result.failure(t)) }
        createSyncCoroutine(pictureKey)
    }
}