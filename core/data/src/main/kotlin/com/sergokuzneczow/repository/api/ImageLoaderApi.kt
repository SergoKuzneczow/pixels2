package com.sergokuzneczow.repository.api

import android.graphics.Bitmap
import coil3.ImageLoader

public interface ImageLoaderApi {

    public val imageLoader : ImageLoader

    public suspend fun loadBitmap(
        path: String,
        onStart: () -> Unit,
        onSuccess: (bitmap: Bitmap) -> Unit,
        onError: (throwable: Throwable) -> Unit
    )
}