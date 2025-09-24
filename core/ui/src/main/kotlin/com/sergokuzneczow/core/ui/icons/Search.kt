package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Search: ImageVector
    get() {
        if (_search != null) {
            return _search!!
        }
        _search =  ImageVector.Builder(
            name = "Search",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1.01979f
            ) {
                moveTo(36.898f, 1.444f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 2.078f, 30.194f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 29.999f, 65.584f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 52.287f, 59.259f)
                lineToRelative(34.367f, 35.079f)
                curveToRelative(0.453f, 0.463f, 1.19f, 0.469f, 1.652f, 0.015f)
                lineToRelative(4.737f, -4.66f)
                curveToRelative(0.462f, -0.454f, 0.469f, -1.192f, 0.015f, -1.655f)
                lineTo(58.584f, 52.848f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 64.82f, 36.834f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 36.898f, 1.444f)
                close()
                moveTo(35.862f, 8.402f)
                arcTo(24.959f, 25.521f, 8.291f, isMoreThanHalf = false, isPositiveArc = true, 57.955f, 36.404f)
                arcTo(24.959f, 25.521f, 8.291f, isMoreThanHalf = false, isPositiveArc = true, 30.404f, 59.151f)
                arcTo(24.959f, 25.521f, 8.291f, isMoreThanHalf = false, isPositiveArc = true, 8.311f, 31.15f)
                arcTo(24.959f, 25.521f, 8.291f, isMoreThanHalf = false, isPositiveArc = true, 35.862f, 8.402f)
                close()
            }
        }.build()

        return _search!!
    }

@Suppress("ObjectPropertyName")
private var _search: ImageVector? = null
