package com.sergokuzneczow.core.ui.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

val PixelsIcons.Top: ImageVector
    get() {
        if (_top != null) {
            return _top!!
        }
        _top = ImageVector.Builder(
            name = "Top",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFDC8A00)),
                fillAlpha = 0.34887007f,
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
                        0.001f to Color(0xFFDC9000),
                        1f to Color(0xBAFFCD2B)
                    ),
                    start = Offset(32.545f, 18.776f),
                    end = Offset(72.831f, 85.824f)
                ),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveTo(18.278f, 13.69f)
                lineTo(87.098f, 13.69f)
                arcTo(4.2f, 4.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 91.298f, 17.89f)
                lineTo(91.298f, 86.71f)
                arcTo(4.2f, 4.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 87.098f, 90.91f)
                lineTo(18.278f, 90.91f)
                arcTo(4.2f, 4.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14.078f, 86.71f)
                lineTo(14.078f, 17.89f)
                arcTo(4.2f, 4.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 18.278f, 13.69f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFF2F2F2)),
                fillAlpha = 0.905367f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveToRelative(47.699f, 40.713f)
                curveToRelative(-2.327f, 0f, -4.2f, 1.873f, -4.2f, 4.2f)
                verticalLineToRelative(16.877f)
                curveToRelative(0f, 2.327f, 1.873f, 4.2f, 4.2f, 4.2f)
                horizontalLineToRelative(12.327f)
                curveToRelative(2.327f, 0f, 4.2f, -1.873f, 4.2f, -4.2f)
                lineTo(64.226f, 44.912f)
                curveToRelative(0f, -2.327f, -1.873f, -4.2f, -4.2f, -4.2f)
                close()
                moveTo(55.865f, 46.24f)
                horizontalLineToRelative(0.342f)
                lineTo(56.207f, 62.324f)
                lineTo(53.109f, 62.324f)
                lineTo(53.109f, 50.032f)
                lineToRelative(-3.065f, 1.18f)
                verticalLineToRelative(-2.58f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFF2F2F2)),
                fillAlpha = 0.905367f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 0.808433f
            ) {
                moveTo(26.83f, 49.574f)
                lineTo(39.27f, 49.574f)
                arcTo(4.239f, 4.239f, 0f, isMoreThanHalf = false, isPositiveArc = true, 43.509f, 53.813f)
                lineTo(43.509f, 61.704f)
                arcTo(4.239f, 4.239f, 0f, isMoreThanHalf = false, isPositiveArc = true, 39.27f, 65.943f)
                lineTo(26.83f, 65.943f)
                arcTo(4.239f, 4.239f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22.591f, 61.704f)
                lineTo(22.591f, 53.813f)
                arcTo(4.239f, 4.239f, 0f, isMoreThanHalf = false, isPositiveArc = true, 26.83f, 49.574f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFF2F2F2)),
                fillAlpha = 0.905367f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 0.629768f
            ) {
                moveTo(68.446f, 56.372f)
                lineTo(80.993f, 56.372f)
                arcTo(4.275f, 4.275f, 0f, isMoreThanHalf = false, isPositiveArc = true, 85.268f, 60.647f)
                lineTo(85.268f, 61.946f)
                arcTo(4.275f, 4.275f, 0f, isMoreThanHalf = false, isPositiveArc = true, 80.993f, 66.221f)
                lineTo(68.446f, 66.221f)
                arcTo(4.275f, 4.275f, 0f, isMoreThanHalf = false, isPositiveArc = true, 64.171f, 61.946f)
                lineTo(64.171f, 60.647f)
                arcTo(4.275f, 4.275f, 0f, isMoreThanHalf = false, isPositiveArc = true, 68.446f, 56.372f)
                close()
            }
        }.build()

        return _top!!
    }

@Suppress("ObjectPropertyName")
private var _top: ImageVector? = null