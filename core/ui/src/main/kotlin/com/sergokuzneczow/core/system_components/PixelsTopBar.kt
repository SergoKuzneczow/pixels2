package com.sergokuzneczow.core.system_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.ui.pixelsShadow
import com.sergokuzneczow.core.utilites.ThemeUiPreviews


private val TOP_BAR_HEIGHT: Dp = 56.dp

@Composable
public fun PixelsTopBar(
    title: String,
    visibleProgressBar: Boolean,
    modifier: Modifier = Modifier,
    onHomeIconClick: (() -> Unit)? = null,
    onBackIconClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .height(Dimensions.PixelsTopBarBoxHeight)
            .background(Color(0, 0, 0, 0))
    ) {
        Box(
            modifier = modifier
                .padding(horizontal = Dimensions.LargePadding, vertical = Dimensions.LargePadding)
                .pixelsShadow()
                .fillMaxWidth()
                .height(TOP_BAR_HEIGHT)
                .clip(Dimensions.PixelsShape)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
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

            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = Dimensions.Padding)
            ) {
                onBackIconClick?.let {
                    IconButton(
                        onClick = { it.invoke() },
                        modifier = Modifier
                            .padding(Dimensions.SmallPadding)
                            .size(40.dp),
                        colors = iconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        content = {
                            Icon(
                                imageVector = PixelsIcons.arrowBack,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
                onHomeIconClick?.let {
                    IconButton(
                        onClick = { it.invoke() },
                        modifier = Modifier
                            .padding(Dimensions.SmallPadding)
                            .size(40.dp),
                        colors = iconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        content = {
                            Icon(
                                imageVector = PixelsIcons.home,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}

@ThemeUiPreviews
@Composable
private fun PixelsTopBarPreview() {
    PixelsTheme {
        PixelsTopBar(
            title = "Default title",
            visibleProgressBar = true,
            onHomeIconClick = {},
            onBackIconClick = {},
        )
    }
}