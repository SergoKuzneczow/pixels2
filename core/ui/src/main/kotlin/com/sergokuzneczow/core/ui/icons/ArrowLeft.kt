package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.ArrowLeft: ImageVector
    get() {
        if (_arrowLeft != null) {
            return _arrowLeft!!
        }
        _arrowLeft = ImageVector.Builder(
            name = "ArrowLeft",
            defaultWidth = 7.dp,
            defaultHeight = 12.dp,
            viewportWidth = 7f,
            viewportHeight = 12f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                strokeLineWidth = 1f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6.71f, 9.88f)
                lineTo(2.83f, 6f)
                lineTo(6.71f, 2.12f)
                curveTo(7.1f, 1.73f, 7.1f, 1.1f, 6.71f, 0.71f)
                curveTo(6.32f, 0.32f, 5.69f, 0.32f, 5.3f, 0.71f)
                lineTo(0.71f, 5.3f)
                curveTo(0.32f, 5.69f, 0.32f, 6.32f, 0.71f, 6.71f)
                lineTo(5.3f, 11.3f)
                curveTo(5.69f, 11.69f, 6.32f, 11.69f, 6.71f, 11.3f)
                curveTo(7.09f, 10.91f, 7.1f, 10.27f, 6.71f, 9.88f)
                close()
            }
        }.build()

        return _arrowLeft!!
    }

@Suppress("ObjectPropertyName")
private var _arrowLeft: ImageVector? = null
