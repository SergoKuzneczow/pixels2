package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Home: ImageVector
    get() {
        if (_home != null) {
            return _home!!
        }
        _home =  ImageVector.Builder(
            name = "Home",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(46.34f, 20.52f)
                lineToRelative(-21f, -19f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.68f, 0f)
                lineToRelative(-21f, 19f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = false, 2.68f, 3f)
                lineTo(24f, 5.7f)
                lineTo(43.66f, 23.48f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.68f, -3f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(42f, 26f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2f, 2f)
                verticalLineTo(43f)
                horizontalLineTo(8f)
                verticalLineTo(28f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4f, 0f)
                verticalLineTo(45f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 2f)
                horizontalLineTo(42f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, -2f)
                verticalLineTo(28f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 42f, 26f)
                close()
            }
        }.build()

        return _home!!
    }

@Suppress("ObjectPropertyName")
private var _home: ImageVector? = null
