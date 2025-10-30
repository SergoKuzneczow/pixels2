package com.sergokuzneczow.core.system_components.progress_indicators

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun BoxScope.PixelsProgressIndicator(size: Dp = 96.dp) {
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
        ItemProgressIndicator(PixelsIcons.pixelsBlue, a1.value, size / 2, Alignment.TopStart)
        ItemProgressIndicator(PixelsIcons.pixelsRed, a2.value, size / 2, Alignment.TopEnd)
        ItemProgressIndicator(PixelsIcons.pixelsGreen, a3.value, size / 2, Alignment.BottomStart)
        ItemProgressIndicator(PixelsIcons.pixelsYellow, a4.value, size / 2, Alignment.BottomEnd)
    }
}

@Composable
private fun BoxScope.ItemProgressIndicator(
    imageVector: ImageVector,
    itemSize: Dp,
    containerSize: Dp,
    alignment: Alignment,
) {
    Box(
        modifier = Modifier
            .size(containerSize)
            .align(alignment)
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .size(itemSize)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.Center)
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