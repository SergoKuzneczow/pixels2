package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Settings: ImageVector
    get() {
        if (_settings != null) {
            return _settings!!
        }
        _settings = ImageVector.Builder(
            name = "Settings",
            defaultWidth = 311.09.dp,
            defaultHeight = 311.09.dp,
            viewportWidth = 311.09f,
            viewportHeight = 311.09f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(174.04f, 311.08f)
                horizontalLineToRelative(-39.05f)
                curveToRelative(-8.19f, 0f, -15.12f, -5.88f, -16.46f, -13.96f)
                lineToRelative(-4.59f, -28.18f)
                curveToRelative(-2.08f, -0.76f, -4.15f, -1.59f, -6.18f, -2.45f)
                lineTo(85.07f, 283.76f)
                curveToRelative(-6.28f, 4.76f, -15.83f, 4.29f, -21.58f, -1.16f)
                lineTo(35.15f, 255.76f)
                curveToRelative(-5.63f, -5.35f, -6.81f, -14.06f, -2.81f, -20.72f)
                lineToRelative(15.76f, -26.19f)
                curveToRelative(-1.85f, -3.88f, -3.51f, -7.89f, -4.94f, -11.96f)
                lineToRelative(-30.07f, -6.62f)
                curveTo(5.36f, 188.56f, 0f, 181.86f, 0f, 173.97f)
                lineToRelative(0.02f, -39.05f)
                curveToRelative(0.02f, -8.22f, 5.91f, -15.14f, 13.99f, -16.45f)
                lineToRelative(30.65f, -4.99f)
                curveToRelative(1.22f, -3.07f, 2.58f, -6.11f, 4.04f, -9.07f)
                lineToRelative(-16.85f, -27.58f)
                curveToRelative(-4.06f, -6.64f, -2.93f, -15.33f, 2.66f, -20.72f)
                lineTo(62.73f, 29.08f)
                curveToRelative(5.66f, -5.51f, 15.16f, -6.11f, 21.55f, -1.31f)
                lineToRelative(25.66f, 19.27f)
                curveToRelative(1.2f, -0.48f, 2.4f, -0.94f, 3.63f, -1.38f)
                lineToRelative(4.98f, -31.69f)
                curveToRelative(1.32f, -8.08f, 8.26f, -13.96f, 16.45f, -13.96f)
                horizontalLineToRelative(39.05f)
                curveToRelative(7.78f, 0f, 14.63f, 5.51f, 16.29f, 13.11f)
                lineToRelative(7.15f, 32.52f)
                curveToRelative(1.11f, 0.41f, 2.21f, 0.79f, 3.3f, 1.24f)
                lineToRelative(27.47f, -18.65f)
                curveToRelative(6.34f, -4.27f, 15.3f, -3.56f, 20.84f, 1.69f)
                lineToRelative(28.36f, 26.84f)
                curveToRelative(5.98f, 5.66f, 6.95f, 14.7f, 2.31f, 21.49f)
                lineToRelative(-17.54f, 25.82f)
                curveToRelative(1.55f, 3.11f, 2.96f, 6.28f, 4.24f, 9.51f)
                lineToRelative(30.67f, 5.01f)
                curveToRelative(8.1f, 1.32f, 13.98f, 8.24f, 13.98f, 16.45f)
                lineToRelative(-0.02f, 39.05f)
                curveToRelative(0f, 7.78f, -5.52f, 14.63f, -13.11f, 16.27f)
                lineToRelative(-30.07f, 6.58f)
                curveToRelative(-1.29f, 3.69f, -2.75f, 7.29f, -4.39f, 10.78f)
                lineToRelative(16.75f, 24.33f)
                curveToRelative(4.64f, 6.76f, 3.72f, 15.79f, -2.19f, 21.48f)
                lineToRelative(-28.18f, 27.03f)
                curveToRelative(-5.52f, 5.31f, -14.47f, 6.07f, -20.84f, 1.82f)
                lineToRelative(-24.56f, -16.45f)
                curveToRelative(-2.52f, 1.13f, -5.12f, 2.19f, -7.8f, 3.16f)
                lineToRelative(-6.34f, 28.89f)
                curveTo(188.67f, 305.57f, 181.82f, 311.08f, 174.04f, 311.08f)
                close()
                moveTo(137.21f, 291.81f)
                horizontalLineToRelative(34.74f)
                lineToRelative(7.06f, -32.13f)
                curveToRelative(0.74f, -3.37f, 3.23f, -6.09f, 6.51f, -7.13f)
                curveToRelative(5.35f, -1.68f, 10.46f, -3.71f, 15.37f, -6.21f)
                curveToRelative(3.12f, -1.59f, 6.86f, -1.36f, 9.74f, 0.58f)
                lineToRelative(27.39f, 18.33f)
                lineToRelative(25.08f, -24.05f)
                lineToRelative(-18.7f, -27.17f)
                curveToRelative(-2.03f, -2.96f, -2.26f, -6.79f, -0.56f, -9.97f)
                curveToRelative(3f, -5.65f, 5.49f, -11.72f, 7.39f, -18.05f)
                curveToRelative(1.01f, -3.35f, 3.74f, -5.89f, 7.16f, -6.64f)
                lineToRelative(33.4f, -7.32f)
                lineToRelative(0.02f, -34.74f)
                lineToRelative(-34.04f, -5.58f)
                curveToRelative(-3.51f, -0.58f, -6.42f, -3.05f, -7.57f, -6.42f)
                curveToRelative(-2.05f, -6.03f, -4.53f, -11.61f, -7.62f, -17.05f)
                curveToRelative(-1.8f, -3.19f, -1.66f, -7.15f, 0.42f, -10.16f)
                lineToRelative(19.57f, -28.83f)
                lineToRelative(-25.23f, -23.89f)
                lineTo(209.54f, 64.23f)
                curveToRelative(-0.3f, 0.37f, -0.6f, 0.69f, -0.94f, 0.97f)
                curveToRelative(-2.86f, 2.56f, -7.02f, 3.16f, -10.5f, 1.52f)
                curveToRelative(-3.9f, -1.84f, -7.99f, -3.25f, -12.14f, -4.55f)
                curveToRelative(-3.88f, -1.24f, -6.56f, -4.8f, -6.7f, -8.88f)
                lineToRelative(-7.32f, -34f)
                horizontalLineToRelative(-34.73f)
                lineToRelative(-5.63f, 34.44f)
                lineToRelative(-0.37f, -0.07f)
                curveToRelative(-0.87f, 3.6f, -3.09f, 7.5f, -6.18f, 8.49f)
                curveToRelative(-4.29f, 1.38f, -8.49f, 2.84f, -12.49f, 4.76f)
                curveToRelative(-3.56f, 1.68f, -7.75f, 1.08f, -10.64f, -1.57f)
                curveToRelative(-0.26f, -0.23f, -0.53f, -0.51f, -0.78f, -0.79f)
                lineTo(74.46f, 44.52f)
                lineTo(49.39f, 68.55f)
                lineToRelative(17.84f, 29.17f)
                curveToRelative(0.21f, 0.28f, 0.44f, 0.56f, 0.62f, 0.87f)
                curveToRelative(2.01f, 3.02f, 2.17f, 6.9f, 0.41f, 10.06f)
                curveToRelative(-2.93f, 5.24f, -5.4f, 10.82f, -7.36f, 16.53f)
                curveToRelative(-1.15f, 3.37f, -4.06f, 5.84f, -7.59f, 6.41f)
                lineToRelative(-34.02f, 5.56f)
                lineToRelative(-0.02f, 34.74f)
                lineToRelative(33.4f, 7.34f)
                curveToRelative(3.41f, 0.76f, 6.16f, 3.3f, 7.16f, 6.65f)
                curveToRelative(1.94f, 6.55f, 4.55f, 12.86f, 7.75f, 18.77f)
                curveToRelative(1.62f, 3f, 1.53f, 6.64f, -0.23f, 9.55f)
                lineToRelative(-17.45f, 28.97f)
                lineToRelative(25.22f, 23.89f)
                lineToRelative(25.57f, -19.45f)
                curveToRelative(2.91f, -2.22f, 6.88f, -2.59f, 10.15f, -0.95f)
                curveToRelative(4.68f, 2.33f, 9.53f, 4.24f, 14.57f, 5.82f)
                curveToRelative(3.46f, 1.09f, 6.02f, 4.04f, 6.62f, 7.62f)
                lineTo(137.21f, 291.81f)
                close()
                moveTo(156.55f, 214.18f)
                curveToRelative(-31.39f, 0f, -56.94f, -25.55f, -56.94f, -56.94f)
                curveToRelative(0f, -31.41f, 25.55f, -56.96f, 56.94f, -56.96f)
                curveToRelative(31.41f, 0f, 56.96f, 25.55f, 56.96f, 56.96f)
                curveTo(213.53f, 188.63f, 187.98f, 214.18f, 156.55f, 214.18f)
                close()
                moveTo(156.55f, 119.55f)
                curveToRelative(-20.77f, 0f, -37.67f, 16.91f, -37.67f, 37.69f)
                curveToRelative(0f, 20.77f, 16.91f, 37.67f, 37.67f, 37.67f)
                curveToRelative(20.79f, 0f, 37.69f, -16.91f, 37.69f, -37.67f)
                curveTo(194.24f, 136.45f, 177.34f, 119.55f, 156.55f, 119.55f)
                close()
            }
        }.build()

        return _settings!!
    }

@Suppress("ObjectPropertyName")
private var _settings: ImageVector? = null
