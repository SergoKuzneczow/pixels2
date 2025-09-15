package com.sergokuzneczow.repository.impl

import android.graphics.Bitmap
import android.net.Uri
import com.sergokuzneczow.datastore_picture.api.StoragePictureApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import jakarta.inject.Inject

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
}