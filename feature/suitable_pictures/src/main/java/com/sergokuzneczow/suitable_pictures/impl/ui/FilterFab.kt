package com.sergokuzneczow.suitable_pictures.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
internal fun BoxScope.FilterFub(
    modifier: Modifier = Modifier,
    onCLick: () -> Unit = {},
) {

    FloatingActionButton(
        onClick = { onCLick.invoke() },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
            .padding(16.dp)
            .size(56.dp)
            .align(Alignment.BottomEnd)
    ) {
        Image(
            imageVector = PixelsIcons.filter,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.size(24.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun FilterFabPreview() {
    PixelsTheme {
        Box {
            FilterFub()
        }
    }
}