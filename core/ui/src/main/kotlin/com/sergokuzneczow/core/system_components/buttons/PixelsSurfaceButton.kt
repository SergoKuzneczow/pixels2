package com.sergokuzneczow.core.system_components.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

private val BUTTON_HEIGHT: Dp = 40.dp
private val BUTTON_HORIZONTAL_PADDING: Dp = 24.dp
private val ICON_SIZE: Dp = 16.dp
private val BORDER_WIDTH: Dp = 1.dp

@Composable
public fun PixelsSurfaceButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    icon: ImageVector? = null,
    iconContent: (@Composable (Modifier) -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    isVisibleProgress: Boolean = false,
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    Surface(
        onClick = onClick,
        modifier = modifier
            .height(BUTTON_HEIGHT)
            .semantics { role = Role.Button },
        enabled = enabled,
        shape = Dimensions.ButtonCornerShape,
        color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(BORDER_WIDTH, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
        shadowElevation = 2.dp,
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.height(BUTTON_HEIGHT),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.widthIn(min = BUTTON_HORIZONTAL_PADDING),
                    content = {
                        AnimatedVisibility(
                            visible = isVisibleProgress,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier,
                            content = {
                                PixelsProgressIndicator(Dimensions.VerySmallProgressBarSize)
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        )
                    }
                )

                icon?.let {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(ICON_SIZE)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )

                Spacer(modifier = Modifier.widthIn(min = BUTTON_HORIZONTAL_PADDING))
            }
        )
    }
}

@ThemePreviews
@Composable
private fun PixelsSurfaceButtonWithIconPreview() {
    PixelsTheme {
        Surface {
            Box {
                PixelsSurfaceButton(
                    text = "Button preview",
                    onClick = {},
                    icon = PixelsIcons.selector,
                    isVisibleProgress = true,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun PixelsSurfaceButtonWithoutIconPreview() {
    PixelsTheme {
        Surface {
            Box {
                PixelsSurfaceButton(
                    text = "Button preview",
                    onClick = {},
                    icon = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                )
            }
        }
    }
}