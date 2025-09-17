package com.sergokuzneczow.pixels2.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.pixels2.navigation.PixelsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun PixelsRoot(applicationState: PixelsState) {
    PixelsNavHost(
        applicationState = applicationState,
        modifier = Modifier.fillMaxSize()
    )
}