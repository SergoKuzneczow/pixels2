package com.sergokuzneczow.domain.picture_load_and_save_use_case

import android.graphics.Bitmap
import android.net.Uri
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import jakarta.inject.Inject

public class LoadAndSavePictureUseCase @Inject constructor(
    private val imageLoaderApi: ImageLoaderApi,
    private val storageRepositoryApi: StorageRepositoryApi,
) {

    public suspend fun execute(picturePath: String, result: (result: Result<Uri>) -> Unit) {
        val loadResult: Result<Bitmap> = imageLoaderApi.loadBitmapAwait(picturePath)
        var saveResult: Result<Uri>? = null

        loadResult.onFailure { throwable ->
            result.invoke(Result.failure(throwable))
        }.onSuccess { bitmap ->
            saveResult = storageRepositoryApi.savePictureAwait(bitmap)
        }

        saveResult?.onFailure { throwable ->
            result.invoke(Result.failure(throwable))
        }?.onSuccess { uri ->
            result.invoke(Result.success(uri))
        }
    }
}