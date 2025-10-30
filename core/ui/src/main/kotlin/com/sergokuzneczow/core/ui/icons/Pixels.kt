package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Pixels: ImageVector
    get() {
        if (_icon != null) {
            return _icon!!
        }
        _icon = ImageVector.Builder(
            name = "Pixels",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color.Blue),
                strokeLineWidth = 0.359841f
            ) {
                moveTo(4.719f, 0f)
                lineTo(43.281f, 0f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 48f, 4.719f)
                lineTo(48f, 43.281f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 43.281f, 48f)
                lineTo(4.719f, 48f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 43.281f)
                lineTo(0f, 4.719f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.719f, 0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF55D400)),
                strokeLineWidth = 0.359841f
            ) {
                moveTo(4.719f, 48f)
                lineTo(43.281f, 48f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 48f, 52.719f)
                lineTo(48f, 91.281f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 43.281f, 96f)
                lineTo(4.719f, 96f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 91.281f)
                lineTo(0f, 52.719f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.719f, 48f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFD42A)),
                strokeLineWidth = 0.359841f
            ) {
                moveTo(52.719f, 48f)
                lineTo(91.281f, 48f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 96f, 52.719f)
                lineTo(96f, 91.281f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 91.281f, 96f)
                lineTo(52.719f, 96f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 48f, 91.281f)
                lineTo(48f, 52.719f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 52.719f, 48f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFD40000)),
                strokeLineWidth = 0.359841f
            ) {
                moveTo(52.719f, 0f)
                lineTo(91.281f, 0f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 96f, 4.719f)
                lineTo(96f, 43.281f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 91.281f, 48f)
                lineTo(52.719f, 48f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 48f, 43.281f)
                lineTo(48f, 4.719f)
                arcTo(4.719f, 4.719f, 0f, isMoreThanHalf = false, isPositiveArc = true, 52.719f, 0f)
                close()
            }
        }.build()

        return _icon!!
    }

@Suppress("ObjectPropertyName")
private var _icon: ImageVector? = null
