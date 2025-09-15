package com.sergokuzneczow.repository.api

import android.graphics.Bitmap
import android.net.Uri

public interface StorageRepositoryApi {

    public fun savePictureBitmapToPictureDir(bitmap: Bitmap, onStart: () -> Unit, onSuccess: (uri: Uri) -> Unit, onFailure: (e: Exception) -> Unit)
}