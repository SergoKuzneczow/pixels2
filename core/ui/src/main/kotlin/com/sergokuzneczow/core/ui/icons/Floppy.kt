package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Floppy: ImageVector
    get() {
        if (icon != null) {
            return icon!!
        }
        icon = ImageVector.Builder(
            name = "Floppy",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 444.83f,
            viewportHeight = 444.83f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(343.68f, 0f)
                horizontalLineToRelative(-319.6f)
                curveToRelative(-9.35f, 0f, -17f, 7.65f, -17f, 17f)
                verticalLineToRelative(410.83f)
                curveToRelative(0f, 9.35f, 7.65f, 17f, 17f, 17f)
                horizontalLineTo(420.75f)
                curveToRelative(9.35f, 0f, 17f, -7.65f, 17f, -17f)
                verticalLineToRelative(-311.95f)
                curveToRelative(0f, -3.68f, -1.13f, -7.37f, -3.68f, -10.48f)
                lineTo(357f, 6.52f)
                curveTo(353.88f, 2.55f, 348.78f, 0f, 343.68f, 0f)
                close()
                moveTo(137.42f, 34f)
                horizontalLineToRelative(178.5f)
                verticalLineToRelative(167.17f)
                horizontalLineToRelative(-178.5f)
                verticalLineTo(34f)
                close()
                moveTo(403.75f, 410.83f)
                horizontalLineTo(41.08f)
                verticalLineTo(34f)
                horizontalLineToRelative(62.33f)
                verticalLineToRelative(184.45f)
                curveToRelative(0f, 9.35f, 6.23f, 16.72f, 15.58f, 16.72f)
                horizontalLineToRelative(214.77f)
                curveToRelative(9.35f, 0f, 16.15f, -7.37f, 16.15f, -16.72f)
                verticalLineTo(53.83f)
                lineToRelative(53.83f, 68f)
                verticalLineTo(410.83f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(276.25f, 82.17f)
                curveToRelative(0f, -9.35f, -7.65f, -17f, -17f, -17f)
                horizontalLineToRelative(-19.83f)
                curveToRelative(-9.35f, 0f, -17f, 7.65f, -17f, 17f)
                verticalLineToRelative(68f)
                curveToRelative(0f, 9.35f, 7.65f, 17f, 17f, 17f)
                horizontalLineToRelative(19.83f)
                curveToRelative(9.35f, 0f, 17f, -7.65f, 17f, -17f)
                verticalLineTo(82.17f)
                close()
            }
        }.build()

        return icon!!
    }

@Suppress("ObjectPropertyName")
private var icon: ImageVector? = null
