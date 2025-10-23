package com.sergokuzneczow.pixels2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.pixels2.view_model.MainActivityViewModel
import com.sergokuzneczow.pixels2.view_model.MainActivityViewModelFactory


internal class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val vm: MainActivityViewModel = viewModel(factory = MainActivityViewModelFactory(LocalContext.current))
            val applicationNotificationChanelId = createApplicationNotificationChannel()
            val applicationState: PixelsState = rememberPixelsState(
                networkMonitor = vm.getNetworkState(),
                applicationNotificationChanelId = applicationNotificationChanelId,
                saveService = { picturePath ->
                    vm.getSavePictureServiceProvider().execute(picturePath, applicationNotificationChanelId = applicationNotificationChanelId)
                }
            )
            val themeState: ApplicationSettings.SystemSettings.ThemeState? by vm.themeState.collectAsStateWithLifecycle()

            PixelsTheme(
                darkTheme = when (themeState) {
                    ApplicationSettings.SystemSettings.ThemeState.LIGHT -> false
                    ApplicationSettings.SystemSettings.ThemeState.DARK -> true
                    ApplicationSettings.SystemSettings.ThemeState.SYSTEM -> isSystemInDarkTheme()
                    null -> isSystemInDarkTheme()
                },
            ) {
                Surface {
                    PixelsRoot(applicationState)
                }
            }
        }
    }

    private fun createApplicationNotificationChannel(): String {
        val chanelId = "message_chanel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                chanelId,
                "Application messages channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager: NotificationManager? =
                this.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
        return chanelId
    }
}