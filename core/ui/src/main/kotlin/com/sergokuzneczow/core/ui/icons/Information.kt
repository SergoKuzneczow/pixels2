package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Information: ImageVector
    get() {
        if (_information != null) {
            return _information!!
        }
        _information = ImageVector.Builder(
            name = "Information",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveTo(57.037f, 27.237f)
                curveTo(56.969f, 27.282f, 56.895f, 27.32f, 56.816f, 27.35f)
                lineTo(39.178f, 34.091f)
                lineTo(39.178f, 93.144f)
                curveTo(39.178f, 93.774f, 39.686f, 94.282f, 40.316f, 94.282f)
                lineTo(55.988f, 94.282f)
                curveTo(56.618f, 94.282f, 57.125f, 93.774f, 57.125f, 93.144f)
                lineTo(57.125f, 27.678f)
                curveTo(57.125f, 27.521f, 57.094f, 27.373f, 57.037f, 27.237f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveTo(42.465f, 1.643f)
                lineTo(54.218f, 1.643f)
                arcTo(1.137f, 1.137f, 0f, isMoreThanHalf = false, isPositiveArc = true, 55.356f, 2.78f)
                lineTo(55.356f, 16.177f)
                arcTo(1.137f, 1.137f, 0f, isMoreThanHalf = false, isPositiveArc = true, 54.218f, 17.314f)
                lineTo(42.465f, 17.314f)
                arcTo(1.137f, 1.137f, 0f, isMoreThanHalf = false, isPositiveArc = true, 41.327f, 16.177f)
                lineTo(41.327f, 2.78f)
                arcTo(1.137f, 1.137f, 0f, isMoreThanHalf = false, isPositiveArc = true, 42.465f, 1.643f)
                close()
            }
        }.build()

        return _information!!
    }

@Suppress("ObjectPropertyName")
private var _information: ImageVector? = null
