package com.sergokuzneczow.core.system_components.progress_indicators

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val CIRCULAR_PROGRESS_INDICATOR_SIZE: Dp = 24.dp
private val CIRCULAR_PROGRESS_INDICATOR_STROKE_WIDTH: Dp = 3.dp

@Composable
public fun BoxScope.PixelsCircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        strokeWidth = CIRCULAR_PROGRESS_INDICATOR_STROKE_WIDTH,
        modifier = modifier
            .size(CIRCULAR_PROGRESS_INDICATOR_SIZE)
            .align(Alignment.Center)
    )
}

@Composable
public fun PixelsCircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        strokeWidth = CIRCULAR_PROGRESS_INDICATOR_STROKE_WIDTH,
        modifier = modifier
            .size(CIRCULAR_PROGRESS_INDICATOR_SIZE)
    )
}