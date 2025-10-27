package com.sergokuzneczow.service_save_picture.api

import android.content.Context
import android.content.Intent
import android.os.Build
import com.sergokuzneczow.service_save_picture.impl.PictureSavingService

//public interface PictureSavingServiceProviderApi {
//
//    public fun execute(
//        picturePath: String,
//        applicationNotificationChanelId: String = "",
//    )
//}

public fun Context.createPictureSavingService(
    picturePath: String,
    applicationNotificationChanelId: String,
) {
    val intent: Intent = Intent(this, PictureSavingService::class.java).apply {
        this.putExtra(PictureSavingService.PICTURE_PATH_KEY, picturePath)
        this.putExtra(PictureSavingService.APPLICATION_NOTIFICATION_ID_KEY, applicationNotificationChanelId)
    }
    if (Build.VERSION.SDK_INT >= 26) this.startForegroundService(intent)
}