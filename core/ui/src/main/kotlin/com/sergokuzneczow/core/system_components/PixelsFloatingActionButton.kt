package com.sergokuzneczow.core.system_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

private val FloatingActionButtonSize: Dp = 56.dp
private val FloatingActionButtonPadding: Dp = 16.dp
private val IconSize: Dp = 24.dp

@Composable
public fun BoxScope.PixelsPrimaryFloatingActionButton(
    imageVector: ImageVector,
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = { onCLick.invoke() },
        modifier = modifier
            .padding(FloatingActionButtonPadding)
            .size(FloatingActionButtonSize)
            .align(Alignment.BottomEnd),
        shape = Dimensions.PixelsShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.size(IconSize)
        )
    }
}

@Composable
public fun PixelsPrimaryFloatingActionButton(
    imageVector: ImageVector,
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = { onCLick.invoke() },
        modifier = modifier
            .padding(FloatingActionButtonPadding)
            .size(FloatingActionButtonSize),
        shape = Dimensions.PixelsShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier.size(IconSize)
        )
    }
}

@ThemePreviews
@Composable
private fun FilterFabPreview() {
    PixelsTheme {
        Box {
            PixelsPrimaryFloatingActionButton(
                imageVector = PixelsIcons.filter,
                onCLick = {},
            )
        }
    }
}