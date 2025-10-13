package com.sergokuzneczow.pixels2

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergokuzneczow.core.system_components.PixelsScaffold
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.pixels2.ui.PixelsNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun PixelsRoot(applicationState: PixelsState) {
    val networkStateIsOffline: Boolean by applicationState.isOffline.collectAsStateWithLifecycle()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    PixelsScaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(horizontal = Dimensions.LargePadding, vertical = 80.dp)
            )
        },
        content = {
            PixelsNavHost(
                applicationState = applicationState,
                onShowSnackbar = { message, action ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = action,
                        duration = SnackbarDuration.Short,
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    )

    LaunchedEffect(networkStateIsOffline) {
        if (networkStateIsOffline) {
            snackbarHostState.showSnackbar(
                message = "Not connected",
                duration = Indefinite,
            )
        }
    }
}