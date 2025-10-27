package com.sergokuzneczow.service_save_picture.impl

import android.content.Context
import android.content.Intent
import android.os.Build

//@Singleton
//public class PictureSavingServiceProviderImpl @Inject constructor(
//    private val context: Context,
//) : PictureSavingServiceProviderApi {

//public fun Context.createPictureSavingService(
//    picturePath: String,
//    applicationNotificationChanelId: String,
//) {
//    val intent: Intent = Intent(this, PictureSavingService::class.java).apply {
//        this.putExtra(PictureSavingService.PICTURE_PATH_KEY, picturePath)
//        this.putExtra(PictureSavingService.APPLICATION_NOTIFICATION_ID_KEY, applicationNotificationChanelId)
//    }
//    if (Build.VERSION.SDK_INT >= 26) this.startForegroundService(intent)
//}
//}