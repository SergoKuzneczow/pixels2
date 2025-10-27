package com.sergokuzneczow.pixels2

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.ui.R
import com.sergokuzneczow.pixels2.ui.PixelsScreen
import com.sergokuzneczow.pixels2.view_model.MainActivityViewModel
import com.sergokuzneczow.pixels2.view_model.MainActivityViewModelFactory


internal class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val vm: MainActivityViewModel = viewModel(factory = MainActivityViewModelFactory(LocalContext.current))

            val applicationState: PixelsState = rememberPixelsState(
                applicationNotificationChanelId = createApplicationNotificationChannel(),
                darkThemeMonitor = vm.themeState.collectAsStateWithLifecycle(),
                networkMonitor = vm.networkState.collectAsStateWithLifecycle(),
                toastMonitor = vm.toastState.collectAsStateWithLifecycle()
            )

            PixelsTheme(
                darkTheme = applicationState.isDark.value ?: isSystemInDarkTheme(),
                content = {
                    Surface {
                        PixelsScreen(
                            applicationState = applicationState,
                            onShowNotification = { chanelId, intent, title, message ->
                                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
                                    showNotification(chanelId = applicationState.applicationNotificationChanelId, intent, title, message)
                                else vm.setToast("$title: $message")
                            },
                            onSavePicture = vm::loadAndSavePicture,
                        )
                    }
                }
            )
        }
    }

    private fun createApplicationNotificationChannel(): String {
        val chanelId = "message_chanel_id"
        val channelName = "Application messages channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(chanelId, channelName, importance)
            val manager: NotificationManager? =
                this.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
        return chanelId
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification(
        chanelId: String,
        intent: Intent,
        title: String,
        message: String,
    ) {
        val pendingIntent: PendingIntent? = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification: Notification = NotificationCompat.Builder(this, chanelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_selector)
            .setContentIntent(pendingIntent)
            .build()
        NotificationManagerCompat.from(this).notify(intent.hashCode(), notification)
    }
}