package com.sergokuzneczow.pixels2

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
            val applicationState: PixelsState = rememberPixelsState(
                networkMonitor = vm.getNetworkState(),
                saveService = { it ->
                    //createExampleService(it)
                    vm.getSavePictureServiceProvider().execute(it)
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

//    private fun createExampleService(picturePath: String) {
//        val intent = Intent(this, ExampleService::class.java).apply {
//            this.putExtra(ExampleService.DATA_KEY, picturePath)
//        }
//        if (Build.VERSION.SDK_INT >= 26) this.startForegroundService(intent)
//    }
}


//internal class ExampleService() : Service() {
//
//    internal companion object {
//        const val DATA_KEY: String = "picturePath"
//        private const val SERVICE_ID: Int = 1
//        private const val CHANNEL_ID: String = "chanel_id"
//    }
//
//    @Inject
//    lateinit var imageLoaderApi: ImageLoaderApi
//
//    @Inject
//    lateinit var storageRepositoryApi: StorageRepositoryApi
//
//    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
//
//    override fun onCreate() {
//        this.applicationComponent.inject(this)
//        createNotificationChannel()
//    }
//
//    override fun onBind(intent: Intent?): IBinder? = null
//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    override fun onStartCommand(
//        intent: Intent?,
//        flags: Int,
//        startId: Int
//    ): Int {
//        startForeground(
//            SERVICE_ID,
//            getNotification(),
//            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
//        )
//
//        val picturePath: String = intent?.getStringExtra(DATA_KEY) ?: ""
//
//        scope.launch {
//            val loadResult: Result<Bitmap> = imageLoaderApi.loadBitmapAwait(picturePath)
//            if (loadResult.isFailure) {
//                cancel()
//            } else {
//                val bitmap: Bitmap = loadResult.getOrThrow()
//                val saveResult: Result<Uri> = storageRepositoryApi.savePictureAwait(bitmap)
//                if (saveResult.isFailure) {
//                    cancel()
//                } else {
//                    val uri: Uri = saveResult.getOrThrow()
//                }
//            }
//            stopForeground(STOP_FOREGROUND_REMOVE)
//            stopSelf()
//        }
//
//        return START_STICKY
//    }
//
//    override fun onDestroy() {
//        scope.cancel()
//        super.onDestroy()
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val serviceChannel = NotificationChannel(
//                CHANNEL_ID, // id
//                "Foreground Service Channel", // name
//                NotificationManager.IMPORTANCE_DEFAULT // importance
//            )
//            val manager: NotificationManager? =
//                getSystemService(NotificationManager::class.java)
//            manager?.createNotificationChannel(serviceChannel)
//        }
//    }
//
//    private fun getNotification(): Notification {
//        val notification: Notification =
//            NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Saving picture...")
//                .setSmallIcon(R.drawable.ic_download)
//                .build()
//
//        return notification
//    }
//}