package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.FailureVariant: ImageVector
    get() {
        if (_icon != null) {
            return _icon!!
        }
        _icon = ImageVector.Builder(
            name = "Failure",
            defaultWidth = 64.dp,
            defaultHeight = 64.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(fill = SolidColor(Color(0xFFF44336))) {
                moveTo(16f, 16f)
                moveToRelative(-14f, 0f)
                arcToRelative(14f, 14f, 0f, isMoreThanHalf = true, isPositiveArc = true, 28f, 0f)
                arcToRelative(14f, 14f, 0f, isMoreThanHalf = true, isPositiveArc = true, -28f, 0f)
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(24.423f, 21.652f)
                lineToRelative(-2.828f, 2.828f)
                lineToRelative(-14.142f, -14.142f)
                lineToRelative(2.828f, -2.828f)
                close()
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(10.295f, 24.466f)
                lineToRelative(-2.828f, -2.828f)
                lineToRelative(14.142f, -14.142f)
                lineToRelative(2.828f, 2.828f)
                close()
            }
        }.build()

        return _icon!!
    }

@Suppress("ObjectPropertyName")
private var _icon: ImageVector? = null
