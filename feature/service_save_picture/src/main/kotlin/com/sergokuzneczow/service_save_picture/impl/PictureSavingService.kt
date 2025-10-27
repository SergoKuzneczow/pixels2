package com.sergokuzneczow.service_save_picture.impl

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.service_save_picture.R.string
import com.sergokuzneczow.core.ui.R
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import com.sergokuzneczow.service_save_picture.impl.di.PictureSavingServiceFeatureComponent
import com.sergokuzneczow.service_save_picture.impl.di.dependenciesProvider
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

internal class PictureSavingService : Service() {

    internal companion object {
        const val PICTURE_PATH_KEY: String = "picture_path"
        const val APPLICATION_NOTIFICATION_ID_KEY: String = "application_notification_id_key"
        private const val SERVICE_ID: Int = 15
        private const val SERVICE_CHANNEL_ID: String = "service_chanel_id"
    }

    @Inject
    lateinit var imageLoaderApi: ImageLoaderApi

    @Inject
    lateinit var storageRepositoryApi: StorageRepositoryApi

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate() {
        createServiceNotificationChannel(this)
        PictureSavingServiceFeatureComponent.Instance
            .get(this.dependenciesProvider.serviceSavePictureFeatureDependenciesProvider())
            .inject(this)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        val picturePath: String = intent?.getStringExtra(PICTURE_PATH_KEY) ?: ""

        try {
            startForeground(
                picturePath.hashCode(),
                createServiceNotification(),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC else 0,
            )
        } catch (e: Exception) {
        }

        scope.launch(Dispatchers.IO) {
            val loadResult: Result<Bitmap> = imageLoaderApi?.loadBitmapAwait(picturePath) ?: throw IllegalStateException("Must be not null.")
            if (loadResult.isFailure) {
                stopSelf()
                cancel()
            } else {
                val bitmap: Bitmap = loadResult.getOrThrow()
                val saveResult: Result<Uri> = storageRepositoryApi?.savePictureAwait(bitmap) ?: throw IllegalStateException("Must be not null.")
                if (saveResult.isFailure) {
                    stopSelf()
                    cancel()
                } else {
                    val applicationChanel: String = intent?.getStringExtra(APPLICATION_NOTIFICATION_ID_KEY) ?: ""
                    val uri: Uri = saveResult.getOrNull() ?: throw IllegalStateException("Unknown uri.")
                    showSuccessNotification(uri, applicationChanel)
                    stopSelf()
                    cancel()
                }
            }
            //stopForeground(STOP_FOREGROUND_REMOVE)
        }

        return START_STICKY
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun createServiceNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                SERVICE_CHANNEL_ID,
                "Picture saving service channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager: NotificationManager? = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun createServiceNotification(): Notification {
        val notification: Notification =
            NotificationCompat.Builder(this, SERVICE_CHANNEL_ID)
                .setContentTitle(getString(string.title_saving_picture))
                .setSmallIcon(R.drawable.ic_download)
                .build()
        return notification
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private fun showSuccessNotification(uri: Uri, chanelId: String = "default") {
        NotificationManagerCompat.from(this)
            .notify(uri.hashCode(), createSuccessNotification(uri, chanelId))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createSuccessNotification(uri: Uri, chanelId: String): Notification {
        val intentGetContent = Intent().apply {
            action = Intent.ACTION_VIEW
            setDataAndType(uri, "image/*")
        }
        val pendingIntent: PendingIntent? = PendingIntent.getActivity(this, 0, intentGetContent, PendingIntent.FLAG_IMMUTABLE)
        val notification: Notification =
            NotificationCompat.Builder(this, chanelId)
                .setContentTitle(getString(string.title_picture_saved))
                .setContentText(getString(string.body_picture_saved) + ":$uri")
                .setSmallIcon(R.drawable.ic_success_variant)
                .setContentIntent(pendingIntent)
                .build()
        return notification
    }
}