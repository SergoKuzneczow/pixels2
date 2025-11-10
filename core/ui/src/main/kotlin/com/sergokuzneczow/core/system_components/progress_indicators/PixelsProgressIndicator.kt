package com.sergokuzneczow.core.system_components.progress_indicators

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun BoxScope.PixelsProgressAnimatedVisibilityIndicator(
    isVisible: Boolean,
    size: Dp = Dimensions.ProgressBarSize,
) {
    Row(modifier = Modifier.align(Alignment.Center)) {
        AnimatedVisibility(isVisible) {
            Box { PixelsProgressIndicator(size) }
        }
    }
}

@Composable
public fun PixelsProgressAnimatedVisibilityIndicator(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = Dimensions.ProgressBarSize,
) {
    Row(modifier = modifier) {
        AnimatedVisibility(isVisible) {
            Box { PixelsProgressIndicator(size) }
        }
    }
}

@Composable
public fun PixelsProgressIndicator(size: Dp = Dimensions.ProgressBarSize) {
    Box { PixelsProgressIndicator(size) }
}

@Composable
public fun BoxScope.PixelsProgressIndicator(size: Dp = Dimensions.ProgressBarSize) {
    val infiniteTransition = rememberInfiniteTransition()
    val a1 = infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = size / 2,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, delayMillis = 100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val a2 = infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = size / 2,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, delayMillis = 200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val a3 = infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = size / 2,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, delayMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val a4 = infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = size / 2,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, delayMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    Box(
        modifier = Modifier
            .size(size)
            .align(Alignment.Center)
    ) {
        ItemProgressIndicator(Color(2, 15, 248, 255), a1.value, size / 2, Alignment.TopStart)
        ItemProgressIndicator(Color(184, 4, 4, 255), a2.value, size / 2, Alignment.TopEnd)
        ItemProgressIndicator(Color(31, 154, 6, 255), a3.value, size / 2, Alignment.BottomStart)
        ItemProgressIndicator(Color(220, 192, 3, 255), a4.value, size / 2, Alignment.BottomEnd)
    }
}

@Composable
private fun BoxScope.ItemProgressIndicator(
    color: Color,
    itemSize: Dp,
    containerSize: Dp,
    alignment: Alignment,
) {
    Box(
        modifier = Modifier
            .size(containerSize)
            .align(alignment)
    ) {
        Spacer(
            modifier = Modifier
                .size(itemSize)
                .align(Alignment.Center)
                .background(color)
        )
    }
}

@ThemePreviews
@Composable
private fun PixelsProgressIndicatorPreview() {
    PixelsTheme {
        Box { PixelsProgressIndicator() }
    }
}