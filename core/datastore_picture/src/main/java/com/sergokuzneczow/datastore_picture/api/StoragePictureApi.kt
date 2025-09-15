package com.sergokuzneczow.datastore_picture.api

import android.graphics.Bitmap
import android.net.Uri

public interface StoragePictureApi {

    public fun savePictureBitmapToPictureDir(
        bitmap: Bitmap,
        onStart: () -> Unit,
        onSuccess: (uri: Uri) -> Unit,
        onFailure: (e: Exception) -> Unit
    )
}