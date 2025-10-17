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
}