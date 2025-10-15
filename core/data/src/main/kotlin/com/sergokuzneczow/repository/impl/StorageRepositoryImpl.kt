package com.sergokuzneczow.repository.impl

import android.graphics.Bitmap
import android.net.Uri
import com.sergokuzneczow.datastore_picture.api.StoragePictureApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import jakarta.inject.Inject
import kotlinx.coroutines.delay

public class StorageRepositoryImpl @Inject constructor(
    private val storagePictureApi: StoragePictureApi,
) : StorageRepositoryApi {

    override fun savePictureBitmapToPictureDir(
        bitmap: Bitmap,
        onStart: () -> Unit,
        onSuccess: (Uri) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        storagePictureApi.savePictureBitmapToPictureDir(bitmap, onStart, onSuccess, onFailure)
    }

    override suspend fun savePictureAwait(bitmap: Bitmap): Result<Uri> {
        var result: Result<Uri>? = null

        storagePictureApi.savePictureBitmapToPictureDir(
            bitmap,
            onStart = {},
            onSuccess = { uri -> result = Result.success(uri) },
            onFailure = { t -> result = Result.failure(t) },
        )

        while (result == null) {
            delay(100)
        }

        return result
    }
}