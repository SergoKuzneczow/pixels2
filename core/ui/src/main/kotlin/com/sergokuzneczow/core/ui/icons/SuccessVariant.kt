package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.SuccessVariant: ImageVector
    get() {
        if (_icon != null) {
            return _icon!!
        }
        _icon = ImageVector.Builder(
            name = "Success",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 512f,
            viewportHeight = 512f
        ).apply {
            path(fill = SolidColor(Color(0xFF55D400))) {
                moveTo(81.32f, 38.4f)
                lineTo(433.64f, 38.4f)
                arcTo(45.86f, 45.86f, 0f, isMoreThanHalf = false, isPositiveArc = true, 479.5f, 84.26f)
                lineTo(479.5f, 426.68f)
                arcTo(45.86f, 45.86f, 0f, isMoreThanHalf = false, isPositiveArc = true, 433.64f, 472.54f)
                lineTo(81.32f, 472.54f)
                arcTo(45.86f, 45.86f, 0f, isMoreThanHalf = false, isPositiveArc = true, 35.46f, 426.68f)
                lineTo(35.46f, 84.26f)
                arcTo(45.86f, 45.86f, 0f, isMoreThanHalf = false, isPositiveArc = true, 81.32f, 38.4f)
                close()
            }
            path(
                fill = SolidColor(Color.White),
                strokeLineWidth = 17.732128f
            ) {
                moveToRelative(347.52f, 165.87f)
                lineToRelative(-120.28f, 120.22f)
                lineToRelative(-54.05f, -53.73f)
                lineToRelative(-25.26f, 25.38f)
                curveToRelative(26.48f, 26.31f, 52.91f, 52.66f, 79.43f, 78.92f)
                curveToRelative(48.49f, -48.49f, 96.98f, -96.98f, 145.47f, -145.47f)
                close()
            }
        }.build()


        return _icon!!
    }

@Suppress("ObjectPropertyName")
private var _icon: ImageVector? = null
