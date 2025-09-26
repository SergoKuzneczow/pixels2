package com.sergokuzneczow.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public data object Dimensions {
    public val PixelsTopBarBoxHeight: Dp = 128.dp
    public val PixelsShape: RoundedCornerShape = RoundedCornerShape(15.dp)
    public val SmallPadding: Dp = 4.dp
    public val Padding: Dp = 8.dp
    public val LargePadding: Dp = 16.dp
    public val ContentPadding: Dp = 4.dp
    public val Elevation: Dp = 4.dp
    public val SmallElevation: Dp = 2.dp
    public val Border: BorderStroke = BorderStroke(1.dp, Color(1, 1, 1, 20))
}

@Composable
public fun Modifier.pixelsShadow(): Modifier = this.shadow(
    elevation = Dimensions.SmallElevation,
    shape = Dimensions.PixelsShape,
    ambientColor = Color(0, 0, 0, 0),
    spotColor = Color(0, 0, 0, 60),
)