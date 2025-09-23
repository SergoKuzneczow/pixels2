package com.sergokuzneczow.pixels2.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.pixelsShadow

@Composable
internal fun BoxScope.NotNetwork(visible: Boolean = true) {
    AnimatedVisibility(
        visible = visible,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = Dimensions.LargePadding + 80.dp)
    ) {
        Box(
            modifier = Modifier
                .pixelsShadow()
                .padding(1.dp)
                .clip(Dimensions.PixelsShape)
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Text(
                text = "Not network!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(Dimensions.Padding)
            )
        }
    }
}