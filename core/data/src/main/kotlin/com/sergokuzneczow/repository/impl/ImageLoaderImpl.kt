package com.sergokuzneczow.repository.impl

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.sergokuzneczow.repository.api.ImageLoaderApi
import jakarta.inject.Inject
import jakarta.inject.Singleton
import okhttp3.OkHttpClient

@Singleton
public class ImageLoaderImpl @Inject constructor(private val context: Context) : ImageLoaderApi {

    private val okHttpClient = OkHttpClient()

    override val imageLoader: ImageLoader = ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }.components {
            add(OkHttpNetworkFetcherFactory(callFactory = { okHttpClient }))
        }.build()
}