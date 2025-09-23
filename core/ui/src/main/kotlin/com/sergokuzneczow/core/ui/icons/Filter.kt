package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

val PixelsIcons.Filter: ImageVector
    get() {
        if (_filter != null) {
            return _filter!!
        }
        _filter = ImageVector.Builder(
            name = "Filter",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveTo(2.401f, 2.828f)
                lineTo(2.148f, 9.399f)
                lineTo(40.063f, 53.507f)
                verticalLineTo(95.747f)
                lineTo(55.765f, 77.828f)
                curveToRelative(0.104f, -0.118f, 0.226f, -0.21f, 0.359f, -0.275f)
                verticalLineTo(53.574f)
                lineTo(94.039f, 9.466f)
                lineTo(93.786f, 2.894f)
                lineTo(50.678f, 3.007f)
                lineToRelative(0.002f, -0.053f)
                close()
            }
        }.build()

        return _filter!!
    }

@Suppress("ObjectPropertyName")
private var _filter: ImageVector? = null
