package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.PixelsRed: ImageVector
    get() {
        if (_icon != null) {
            return _icon!!
        }
        _icon = ImageVector.Builder(
            name = "PixelsRed",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFD40000)),
                strokeLineWidth = 0.719682f
            ) {
                moveTo(9.437f, 0f)
                lineTo(86.563f, 0f)
                arcTo(9.437f, 9.437f, 0f, isMoreThanHalf = false, isPositiveArc = true, 96f, 9.437f)
                lineTo(96f, 86.563f)
                arcTo(9.437f, 9.437f, 0f, isMoreThanHalf = false, isPositiveArc = true, 86.563f, 96f)
                lineTo(9.437f, 96f)
                arcTo(9.437f, 9.437f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 86.563f)
                lineTo(0f, 9.437f)
                arcTo(9.437f, 9.437f, 0f, isMoreThanHalf = false, isPositiveArc = true, 9.437f, 0f)
                close()
            }
        }.build()

        return _icon!!
    }

@Suppress("ObjectPropertyName")
private var _icon: ImageVector? = null
