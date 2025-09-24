package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Search: ImageVector
    get() {
        if (_search != null) {
            return _search!!
        }
        _search = ImageVector.Builder(
            name = "Search",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1.01979f
            ) {
                moveTo(32.774f, 1.285f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 2.078f, 30.194f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 29.999f, 65.584f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 52.287f, 59.259f)
                lineTo(86.655f, 94.338f)
                curveTo(87.108f, 94.801f, 87.845f, 94.807f, 88.307f, 94.353f)
                lineTo(93.043f, 89.693f)
                curveTo(93.505f, 89.239f, 93.512f, 88.501f, 93.059f, 88.038f)
                lineTo(58.584f, 52.848f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 64.82f, 36.834f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 36.899f, 1.444f)
                arcTo(31.546f, 32.256f, 8.291f, isMoreThanHalf = false, isPositiveArc = false, 32.774f, 1.285f)
                close()
                moveTo(33.555f, 10.163f)
                arcTo(23.265f, 23.265f, 0f, isMoreThanHalf = false, isPositiveArc = true, 56.82f, 33.428f)
                arcTo(23.265f, 23.265f, 0f, isMoreThanHalf = false, isPositiveArc = true, 33.555f, 56.694f)
                arcTo(23.265f, 23.265f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10.289f, 33.428f)
                arcTo(23.265f, 23.265f, 0f, isMoreThanHalf = false, isPositiveArc = true, 33.555f, 10.163f)
                close()
            }
        }.build()

        return _search!!
    }

@Suppress("ObjectPropertyName")
private var _search: ImageVector? = null
