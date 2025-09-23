package com.sergokuzneczow.core.ui.icons


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

internal val PixelsIcons.Views: ImageVector
    get() {
        if (_Views != null) {
            return _Views!!
        }
        _Views = ImageVector.Builder(
            name = "Views",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF1A9E00)),
                fillAlpha = 0.34887007f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveTo(78.12f, 4.503f)
                curveTo(77.978f, 4.498f, 77.834f, 4.501f, 77.689f, 4.512f)
                lineTo(9.046f, 9.451f)
                curveTo(6.725f, 9.618f, 4.991f, 11.62f, 5.158f, 13.941f)
                lineTo(10.097f, 82.584f)
                curveTo(10.254f, 84.759f, 12.023f, 86.419f, 14.155f, 86.481f)
                lineTo(14.155f, 18.102f)
                curveTo(14.155f, 15.775f, 16.028f, 13.902f, 18.355f, 13.902f)
                lineTo(82.575f, 13.902f)
                lineTo(82.179f, 8.399f)
                curveTo(82.022f, 6.224f, 80.253f, 4.564f, 78.12f, 4.503f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.001f to Color(0xFF105A00),
                        1f to Color(0xFF0EAB00)
                    ),
                    start = Offset(32.545f, 18.776f),
                    end = Offset(72.831f, 85.824f)
                ),
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0f,
                strokeLineWidth = 1f
            ) {
                moveTo(18.278f, 13.69f)
                curveTo(15.951f, 13.69f, 14.078f, 15.563f, 14.078f, 17.89f)
                lineTo(14.078f, 86.71f)
                curveTo(14.078f, 89.037f, 15.951f, 90.91f, 18.278f, 90.91f)
                lineTo(87.098f, 90.91f)
                curveTo(89.424f, 90.91f, 91.298f, 89.037f, 91.298f, 86.71f)
                lineTo(91.298f, 17.89f)
                curveTo(91.298f, 15.563f, 89.424f, 13.69f, 87.098f, 13.69f)
                lineTo(18.278f, 13.69f)
                close()
                moveTo(60.192f, 36.155f)
                curveTo(60.874f, 36.173f, 61.556f, 36.217f, 62.238f, 36.293f)
                curveTo(62.647f, 36.345f, 63.057f, 36.385f, 63.465f, 36.448f)
                curveTo(65.489f, 36.761f, 67.482f, 37.346f, 69.387f, 38.236f)
                curveTo(69.741f, 38.414f, 70.098f, 38.58f, 70.447f, 38.771f)
                curveTo(72.488f, 39.89f, 76.871f, 43.151f, 77.227f, 43.4f)
                curveTo(78.16f, 44.054f, 79.071f, 44.753f, 79.962f, 45.489f)
                curveTo(81.827f, 47.041f, 83.706f, 48.571f, 85.526f, 50.201f)
                curveTo(86.565f, 51.22f, 87.568f, 52.288f, 88.403f, 53.563f)
                lineTo(84.974f, 51.774f)
                lineTo(88.335f, 54.364f)
                curveTo(87.067f, 55.351f, 85.806f, 56.352f, 84.513f, 57.291f)
                curveTo(83.13f, 58.238f, 82.825f, 58.482f, 81.351f, 59.34f)
                curveTo(78.51f, 60.992f, 75.529f, 62.25f, 72.49f, 63.242f)
                curveTo(70.054f, 63.997f, 69.464f, 64.219f, 66.981f, 64.831f)
                curveTo(66.07f, 65.056f, 65.158f, 65.289f, 64.237f, 65.449f)
                curveTo(63.385f, 65.597f, 62.525f, 65.672f, 61.667f, 65.753f)
                curveTo(59.353f, 65.97f, 58.824f, 65.937f, 56.505f, 65.983f)
                curveTo(55.643f, 65.958f, 54.781f, 65.952f, 53.92f, 65.908f)
                curveTo(49.738f, 65.693f, 45.57f, 65.097f, 41.453f, 64.179f)
                curveTo(40.666f, 63.988f, 39.875f, 63.816f, 39.091f, 63.606f)
                curveTo(36.104f, 62.806f, 33.172f, 61.674f, 30.454f, 59.947f)
                curveTo(28.696f, 58.717f, 26.933f, 57.495f, 25.177f, 56.258f)
                curveTo(24.905f, 56.571f, 24.637f, 56.887f, 24.373f, 57.208f)
                lineTo(19.296f, 53.355f)
                curveTo(20.149f, 52.607f, 21.005f, 51.865f, 21.827f, 51.065f)
                curveTo(22.669f, 50.262f, 22.889f, 50.047f, 23.754f, 49.242f)
                curveTo(24.1f, 48.92f, 24.44f, 48.587f, 24.796f, 48.28f)
                curveTo(25.867f, 47.359f, 27.018f, 46.583f, 28.13f, 45.739f)
                curveTo(29.216f, 44.986f, 29.419f, 44.812f, 30.551f, 44.16f)
                curveTo(32.309f, 43.146f, 34.143f, 42.342f, 35.978f, 41.563f)
                curveTo(36.463f, 41.363f, 36.945f, 41.155f, 37.432f, 40.964f)
                curveTo(39.908f, 39.992f, 42.428f, 39.187f, 44.961f, 38.468f)
                curveTo(47.971f, 37.674f, 50.998f, 36.946f, 54.06f, 36.52f)
                curveTo(56.098f, 36.281f, 58.146f, 36.102f, 60.192f, 36.155f)
                close()
                moveTo(64.054f, 40.132f)
                curveTo(63.162f, 40.097f, 62.269f, 40.101f, 61.378f, 40.142f)
                curveTo(60.257f, 40.193f, 59.634f, 40.27f, 58.513f, 40.38f)
                curveTo(55.443f, 40.738f, 52.409f, 41.437f, 49.395f, 42.214f)
                curveTo(46.373f, 43.025f, 43.402f, 44.072f, 40.469f, 45.265f)
                curveTo(38.665f, 46.049f, 36.861f, 46.847f, 35.132f, 47.858f)
                curveTo(34.11f, 48.455f, 33.745f, 48.727f, 32.754f, 49.399f)
                curveTo(31.276f, 50.469f, 29.818f, 51.594f, 28.443f, 52.855f)
                curveTo(27.787f, 53.457f, 27.221f, 54.056f, 26.595f, 54.7f)
                curveTo(26.337f, 54.969f, 26.083f, 55.243f, 25.831f, 55.52f)
                lineTo(27.171f, 56.152f)
                curveTo(27.4f, 56.366f, 27.694f, 56.616f, 28.007f, 56.872f)
                curveTo(30.282f, 58.186f, 32.677f, 59.176f, 35.124f, 59.912f)
                curveTo(35.922f, 60.152f, 36.729f, 60.344f, 37.531f, 60.561f)
                curveTo(41.647f, 61.528f, 45.822f, 62.155f, 50.01f, 62.408f)
                curveTo(50.872f, 62.46f, 51.734f, 62.476f, 52.596f, 62.51f)
                curveTo(56.118f, 62.516f, 59.655f, 62.28f, 63.13f, 61.542f)
                curveTo(65.454f, 61.049f, 66.329f, 60.704f, 68.585f, 59.974f)
                curveTo(71.502f, 58.92f, 74.373f, 57.626f, 77.07f, 55.898f)
                curveTo(78.367f, 55.067f, 78.809f, 54.685f, 80.016f, 53.752f)
                curveTo(81.109f, 52.855f, 82.14f, 51.854f, 83.148f, 50.822f)
                lineTo(82.718f, 50.598f)
                curveTo(82.062f, 49.381f, 81.102f, 48.479f, 80.172f, 47.59f)
                curveTo(78.374f, 46.017f, 76.548f, 44.489f, 74.71f, 42.984f)
                curveTo(74.707f, 42.982f, 74.704f, 42.98f, 74.701f, 42.977f)
                curveTo(74.404f, 42.808f, 74.102f, 42.649f, 73.803f, 42.484f)
                curveTo(72.301f, 41.714f, 70.734f, 41.151f, 69.135f, 40.777f)
                curveTo(68.124f, 40.541f, 67.747f, 40.505f, 66.724f, 40.351f)
                curveTo(65.837f, 40.239f, 64.946f, 40.167f, 64.054f, 40.132f)
                close()
                moveTo(71.923f, 40.923f)
                curveTo(72.344f, 41.221f, 72.765f, 41.516f, 73.187f, 41.811f)
                curveTo(73.083f, 41.735f, 72.973f, 41.654f, 72.85f, 41.566f)
                curveTo(72.544f, 41.346f, 71.616f, 40.704f, 71.923f, 40.923f)
                close()
                moveTo(53.757f, 45.227f)
                curveTo(54.385f, 45.23f, 55.007f, 45.35f, 55.631f, 45.425f)
                lineTo(60.607f, 49.913f)
                curveTo(59.742f, 49.722f, 58.874f, 49.573f, 57.995f, 49.513f)
                curveTo(57.573f, 49.511f, 57.15f, 49.542f, 56.73f, 49.607f)
                lineTo(60.843f, 52.502f)
                curveTo(60.31f, 53.513f, 59.678f, 54.442f, 59.014f, 55.33f)
                curveTo(58.617f, 55.793f, 58.435f, 56.04f, 57.985f, 56.426f)
                curveTo(57.287f, 57.025f, 56.484f, 57.379f, 55.665f, 57.648f)
                curveTo(55.238f, 57.737f, 54.979f, 57.813f, 54.548f, 57.82f)
                curveTo(54.318f, 57.824f, 53.784f, 57.788f, 53.567f, 57.644f)
                curveTo(48.291f, 54.142f, 49.572f, 55.456f, 47.628f, 53.38f)
                curveTo(47.521f, 53.217f, 47.402f, 53.064f, 47.308f, 52.889f)
                curveTo(46.796f, 51.934f, 46.713f, 50.789f, 46.939f, 49.703f)
                curveTo(47.089f, 49.147f, 47.108f, 48.968f, 47.357f, 48.466f)
                curveTo(47.76f, 47.652f, 48.375f, 47.098f, 49.057f, 46.662f)
                curveTo(49.267f, 46.544f, 49.472f, 46.415f, 49.685f, 46.307f)
                curveTo(50.796f, 45.743f, 51.985f, 45.387f, 53.182f, 45.254f)
                curveTo(53.374f, 45.245f, 53.565f, 45.227f, 53.757f, 45.227f)
                close()
                moveTo(54.999f, 50.092f)
                curveTo(54.848f, 50.154f, 54.698f, 50.223f, 54.55f, 50.297f)
                curveTo(54.357f, 50.393f, 54.173f, 50.515f, 53.984f, 50.624f)
                curveTo(53.413f, 51.013f, 52.865f, 51.478f, 52.523f, 52.175f)
                curveTo(52.365f, 52.497f, 52.317f, 52.684f, 52.243f, 52.978f)
                curveTo(52.698f, 52.749f, 53.128f, 52.447f, 53.518f, 52.069f)
                curveTo(53.684f, 51.909f, 53.834f, 51.727f, 53.992f, 51.556f)
                curveTo(54.367f, 51.109f, 54.701f, 50.615f, 54.999f, 50.092f)
                close()
                moveTo(53.052f, 56.067f)
                curveTo(53.091f, 56.103f, 53.131f, 56.14f, 53.169f, 56.177f)
                curveTo(54.273f, 56.907f, 54.033f, 56.75f, 53.052f, 56.067f)
                close()
            }
        }.build()

        return _Views!!
    }

@Suppress("ObjectPropertyName")
private var _Views: ImageVector? = null
