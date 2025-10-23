package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Download: ImageVector
    get() {
        if (_download != null) {
            return _download!!
        }
        _download = ImageVector.Builder(
            name = "Download",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                strokeLineWidth = 0.831907f
            ) {
                moveToRelative(81.382f, 83.337f)
                curveToRelative(2.163f, 0f, 3.91f, -1.747f, 3.91f, -3.91f)
                verticalLineToRelative(-24.208f)
                horizontalLineToRelative(9.317f)
                verticalLineToRelative(24.791f)
                curveToRelative(0f, 6.988f, -5.657f, 12.645f, -12.645f, 12.645f)
                horizontalLineTo(14.33f)
                curveToRelative(-6.988f, 0f, -12.645f, -5.657f, -12.645f, -12.645f)
                verticalLineTo(55.219f)
                horizontalLineToRelative(9.317f)
                verticalLineToRelative(24.208f)
                curveToRelative(0f, 2.163f, 1.747f, 3.91f, 3.91f, 3.91f)
                close()
                moveTo(29.804f, 3.474f)
                horizontalLineTo(66.325f)
                verticalLineTo(36.168f)
                horizontalLineTo(80.301f)
                lineTo(48.106f, 67.614f)
                lineTo(15.911f, 36.168f)
                horizontalLineTo(29.887f)
                verticalLineTo(3.474f)
                close()
            }
        }.build()

        return _download!!
    }

@Suppress("ObjectPropertyName")
private var _download: ImageVector? = null
