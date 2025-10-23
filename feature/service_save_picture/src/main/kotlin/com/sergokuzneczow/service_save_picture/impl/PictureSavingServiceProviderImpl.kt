package com.sergokuzneczow.service_save_picture.impl

import android.content.Context
import android.content.Intent
import android.os.Build
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import com.sergokuzneczow.service_save_picture.api.PictureSavingServiceProviderApi
import jakarta.inject.Inject

public class PictureSavingServiceProviderImpl @Inject constructor(
    private val context: Context,
    private val imageLoaderApi: ImageLoaderApi,
    private val storageRepositoryApi: StorageRepositoryApi,
) : PictureSavingServiceProviderApi {

    public override fun execute(
        picturePath: String,
        applicationNotificationChanelId: String,
    ) {
        val intent: Intent = Intent(context, PictureSavingService::class.java).apply {
            this.putExtra(PictureSavingService.PICTURE_PATH_KEY, picturePath)
            this.putExtra(PictureSavingService.APPLICATION_NOTIFICATION_ID_KEY, applicationNotificationChanelId)
        }
        if (Build.VERSION.SDK_INT >= 26) context.startForegroundService(intent)
    }
}