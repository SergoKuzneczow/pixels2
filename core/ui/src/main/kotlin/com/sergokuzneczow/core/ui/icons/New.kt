package com.sergokuzneczow.core.ui.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.New: ImageVector
    get() {
        if (_New != null) {
            return _New!!
        }
        _New = ImageVector.Builder(
            name = "New",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF6F00DC)),
                fillAlpha = 0.235876f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveTo(78.12f, 4.503f)
                curveTo(77.978f, 4.498f, 77.834f, 4.501f, 77.689f, 4.512f)
                lineTo(9.046f, 9.451f)
                curveTo(6.725f, 9.618f, 4.991f, 11.62f, 5.158f, 13.941f)
                lineTo(10.097f, 82.584f)
                curveTo(10.254f, 84.759f, 12.023f, 86.419f, 14.155f, 86.481f)
                lineTo(14.155f, 18.102f)
                curveTo(14.155f, 15.775f, 16.028f, 13.902f, 18.355f, 13.902f)
                lineTo(82.575f, 13.902f)
                lineTo(82.179f, 8.399f)
                curveTo(82.022f, 6.224f, 80.253f, 4.564f, 78.12f, 4.503f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF5700AB),
                        1f to Color(0xFF9C30FF)
                    ),
                    start = Offset(19.887f, 30.999f),
                    end = Offset(85.488f, 73.6f)
                ),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveTo(18.278f, 13.69f)
                curveTo(15.951f, 13.69f, 14.078f, 15.563f, 14.078f, 17.89f)
                lineTo(14.078f, 86.71f)
                curveTo(14.078f, 89.037f, 15.951f, 90.91f, 18.278f, 90.91f)
                lineTo(87.098f, 90.91f)
                curveTo(89.424f, 90.91f, 91.298f, 89.037f, 91.298f, 86.71f)
                lineTo(91.298f, 17.89f)
                curveTo(91.298f, 15.563f, 89.424f, 13.69f, 87.098f, 13.69f)
                lineTo(18.278f, 13.69f)
                close()
                moveTo(41.6f, 35.544f)
                lineTo(48.521f, 35.544f)
                lineTo(59.956f, 58.389f)
                lineTo(59.956f, 35.544f)
                lineTo(66.851f, 35.544f)
                lineTo(66.851f, 71.659f)
                lineTo(59.831f, 71.659f)
                lineTo(48.496f, 49.062f)
                lineTo(48.496f, 71.659f)
                lineTo(41.6f, 71.659f)
                lineTo(41.6f, 35.544f)
                close()
            }
        }.build()

        return _New!!
    }

@Suppress("ObjectPropertyName")
private var _New: ImageVector? = null