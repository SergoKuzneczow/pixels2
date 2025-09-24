package com.sergokuzneczow.pixels2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.pixels2.ui.PixelsRoot

internal class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: MainActivityViewModel = viewModel(factory = MainActivityViewModel.Factory(LocalContext.current))
            val applicationState: PixelsState = rememberPixelsState(
                networkMonitor = vm.getNetworkState(),
            )
            PixelsTheme {
                Surface {
                    PixelsRoot(applicationState)
                }
            }
        }
    }
}