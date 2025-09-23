package com.sergokuzneczow.core.system_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun PixelsTopBar(
    title: String,
    visibleProgressBar: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .shadow(
                elevation = 1.dp,
                shape = CircleShape,
                ambientColor = MaterialTheme.colorScheme.surface,
                spotColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            .padding(start = 1.dp, end = 1.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        )

        AnimatedVisibility(
            visible = visibleProgressBar,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp)
                .align(Alignment.CenterEnd)
        ) {
            PixelsCircularProgressIndicator()
        }
    }
}

@ThemePreviews
@Composable
internal fun PixelsTopBarPreview() {
    PixelsTheme {
        PixelsTopBar(
            title = "Default title",
            visibleProgressBar = true,
        )
    }
}