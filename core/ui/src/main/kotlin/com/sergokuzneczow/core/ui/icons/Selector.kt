package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Selector: ImageVector
    get() {
        if (_selector != null) {
            return _selector!!
        }
        _selector = ImageVector.Builder(
            name = "Selector",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 0.951463f
            ) {
                moveTo(48.215f, 1.366f)
                arcTo(46.596f, 46.596f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.619f, 47.962f)
                arcTo(46.596f, 46.596f, 0f, isMoreThanHalf = false, isPositiveArc = false, 48.215f, 94.559f)
                arcTo(46.596f, 46.596f, 0f, isMoreThanHalf = false, isPositiveArc = false, 94.811f, 47.962f)
                arcTo(46.596f, 46.596f, 0f, isMoreThanHalf = false, isPositiveArc = false, 48.215f, 1.366f)
                close()
                moveTo(48.591f, 10.402f)
                arcTo(38.59f, 38.59f, 0f, isMoreThanHalf = false, isPositiveArc = true, 87.18f, 48.992f)
                arcTo(38.59f, 38.59f, 0f, isMoreThanHalf = false, isPositiveArc = true, 48.591f, 87.582f)
                arcTo(38.59f, 38.59f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10.001f, 48.992f)
                arcTo(38.59f, 38.59f, 0f, isMoreThanHalf = false, isPositiveArc = true, 48.591f, 10.402f)
                close()
                moveTo(48.755f, 22.577f)
                arcTo(26.142f, 26.142f, 0f, isMoreThanHalf = false, isPositiveArc = false, 22.613f, 48.719f)
                arcTo(26.142f, 26.142f, 0f, isMoreThanHalf = false, isPositiveArc = false, 48.755f, 74.861f)
                arcTo(26.142f, 26.142f, 0f, isMoreThanHalf = false, isPositiveArc = false, 74.896f, 48.719f)
                arcTo(26.142f, 26.142f, 0f, isMoreThanHalf = false, isPositiveArc = false, 48.755f, 22.577f)
                close()
            }
        }.build()

        return _selector!!
    }

@Suppress("ObjectPropertyName")
private var _selector: ImageVector? = null
