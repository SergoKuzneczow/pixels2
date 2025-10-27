package com.sergokuzneczow.pixels2.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.system_components.PixelsScaffold
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.pixels2.PixelsState

@Composable
internal fun PixelsScreen(
    applicationState: PixelsState,
    onShowNotification: (chanelId: String, intent: Intent, title: String, message: String) -> Unit,
    onSavePicture: (picturePath: String, block: (result: Result<Uri>) -> Unit) -> Unit,
) {
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
            Box(modifier = Modifier.fillMaxSize()) {
                PixelsNavHost(
                    applicationState = applicationState,
                    onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = SnackbarDuration.Short,
                        )
                    },
                    onShowNotification = onShowNotification,
                    onSavePicture = onSavePicture,
                    modifier = Modifier.fillMaxSize()
                )
                applicationState.toast.value?.let { message -> PixelsToast(message) }
            }
        }
    )

    LaunchedEffect(applicationState.isOnline) {
        if (applicationState.isOnline.value.not()) {
            snackbarHostState.showSnackbar(
                message = "Not connected",
                duration = Indefinite,
            )
        }
    }
}