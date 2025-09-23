package com.sergokuzneczow.pixels2.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergokuzneczow.pixels2.PixelsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun PixelsRoot(applicationState: PixelsState) {
    val networkState: Boolean by applicationState.isOffline.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PixelsNavHost(
            applicationState = applicationState,
            modifier = Modifier.fillMaxSize()
        )
        NotNetwork(visible = networkState)
    }
}