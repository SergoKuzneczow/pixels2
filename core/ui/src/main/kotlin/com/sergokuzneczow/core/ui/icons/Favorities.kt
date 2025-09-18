package com.sergokuzneczow.core.ui.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons

val PixelsIcons.Favorites: ImageVector
    get() {
        if (_favorites != null) {
            return _favorites!!
        }
        _favorites = ImageVector.Builder(
            name = "Likes",
            defaultWidth = 96.dp,
            defaultHeight = 96.dp,
            viewportWidth = 96f,
            viewportHeight = 96f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFDC2D00)),
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
                        0.001f to Color(0xFF990000),
                        1f to Color(0xFFFF5483)
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
                moveTo(36.53f, 27.459f)
                curveTo(37.515f, 27.478f, 38.522f, 27.529f, 39.457f, 27.723f)
                curveTo(42.213f, 28.297f, 48.802f, 31.503f, 49.541f, 31.829f)
                curveTo(51.394f, 33.699f, 52.075f, 34.179f, 53.303f, 36.29f)
                curveTo(53.956f, 37.413f, 54.548f, 38.901f, 54.97f, 40.239f)
                curveTo(55.177f, 39.97f, 55.388f, 39.703f, 55.59f, 39.433f)
                curveTo(57.665f, 36.851f, 59.608f, 34.134f, 62.833f, 32.001f)
                curveTo(63.275f, 31.709f, 63.776f, 31.453f, 64.247f, 31.179f)
                curveTo(64.705f, 30.978f, 65.139f, 30.754f, 65.621f, 30.576f)
                curveTo(66.905f, 30.101f, 68.331f, 29.84f, 69.834f, 29.978f)
                curveTo(71.222f, 30.105f, 71.681f, 30.331f, 72.933f, 30.722f)
                curveTo(75.19f, 31.603f, 77.531f, 32.406f, 79.704f, 33.364f)
                curveTo(80.247f, 33.604f, 80.621f, 33.965f, 81.03f, 34.291f)
                curveTo(81.812f, 34.916f, 82.849f, 35.937f, 83.289f, 36.673f)
                curveTo(83.917f, 37.721f, 84.315f, 39.32f, 84.638f, 40.388f)
                curveTo(85.369f, 43.309f, 85.386f, 46.27f, 84.895f, 49.206f)
                curveTo(84.594f, 51.009f, 84.362f, 51.656f, 83.864f, 53.433f)
                curveTo(83.039f, 55.988f, 81.993f, 58.528f, 80.396f, 60.945f)
                curveTo(79.461f, 62.359f, 78.97f, 62.869f, 77.837f, 64.22f)
                curveTo(75.987f, 66.271f, 73.879f, 68.233f, 71.494f, 70.062f)
                curveTo(70.418f, 70.887f, 69.077f, 71.815f, 67.945f, 72.612f)
                curveTo(64.701f, 74.892f, 60.865f, 76.796f, 57.121f, 78.746f)
                curveTo(55.392f, 79.633f, 53.775f, 80.622f, 51.839f, 81.342f)
                curveTo(51.083f, 81.623f, 50.709f, 81.714f, 49.936f, 81.932f)
                curveTo(49.402f, 82.071f, 48.86f, 82.201f, 48.285f, 82.259f)
                curveTo(48.194f, 82.268f, 48.1f, 82.268f, 48.008f, 82.276f)
                curveTo(47.951f, 82.281f, 47.886f, 82.317f, 47.837f, 82.298f)
                curveTo(45.835f, 81.5f, 43.877f, 80.66f, 41.897f, 79.842f)
                curveTo(42.202f, 79.626f, 42.094f, 79.66f, 42.302f, 79.6f)
                curveTo(42.278f, 79.578f, 42.257f, 79.555f, 42.256f, 79.546f)
                lineTo(42.256f, 79.546f)
                lineTo(42.256f, 79.545f)
                lineTo(42.257f, 79.545f)
                lineTo(42.257f, 79.545f)
                lineTo(42.257f, 79.544f)
                lineTo(42.257f, 79.544f)
                lineTo(42.257f, 79.543f)
                lineTo(42.258f, 79.543f)
                lineTo(42.258f, 79.543f)
                lineTo(42.258f, 79.543f)
                lineTo(42.259f, 79.543f)
                curveTo(42.259f, 79.543f, 42.26f, 79.542f, 42.26f, 79.542f)
                curveTo(42.263f, 79.542f, 42.267f, 79.543f, 42.272f, 79.546f)
                curveTo(42.304f, 79.558f, 42.335f, 79.57f, 42.366f, 79.582f)
                curveTo(42.379f, 79.578f, 42.393f, 79.574f, 42.408f, 79.57f)
                lineTo(42.846f, 79.755f)
                curveTo(43.367f, 79.804f, 43.894f, 79.708f, 44.398f, 79.63f)
                curveTo(45.262f, 79.463f, 45.566f, 79.423f, 46.421f, 79.171f)
                curveTo(48.119f, 78.672f, 49.615f, 77.965f, 51.089f, 77.25f)
                lineTo(47.225f, 76.26f)
                curveTo(46.044f, 75.571f, 44.916f, 74.851f, 43.813f, 74.114f)
                curveTo(41.512f, 72.518f, 39.453f, 70.787f, 37.581f, 68.99f)
                curveTo(37.036f, 68.468f, 36.518f, 67.936f, 35.986f, 67.409f)
                curveTo(33.412f, 64.734f, 31.179f, 61.936f, 29.186f, 59.079f)
                curveTo(28.65f, 58.309f, 28.123f, 57.537f, 27.636f, 56.756f)
                curveTo(27.146f, 55.97f, 26.716f, 55.172f, 26.256f, 54.38f)
                curveTo(25.857f, 53.562f, 25.422f, 52.75f, 25.057f, 51.926f)
                curveTo(24.308f, 50.234f, 23.73f, 48.587f, 23.254f, 46.859f)
                curveTo(22.803f, 45.22f, 22.465f, 43.561f, 22.29f, 41.902f)
                curveTo(22.208f, 41.135f, 22.201f, 40.365f, 22.157f, 39.596f)
                curveTo(22.201f, 38.876f, 22.164f, 38.153f, 22.289f, 37.438f)
                curveTo(22.49f, 36.278f, 23.148f, 34.554f, 23.729f, 33.433f)
                curveTo(24.024f, 32.864f, 24.32f, 32.29f, 24.755f, 31.756f)
                curveTo(25.848f, 30.417f, 26.52f, 30.153f, 28.18f, 29.109f)
                curveTo(30.134f, 28.428f, 31.282f, 27.905f, 33.52f, 27.613f)
                curveTo(34.505f, 27.485f, 35.523f, 27.441f, 36.53f, 27.459f)
                close()
                moveTo(42.366f, 79.582f)
                curveTo(42.342f, 79.589f, 42.321f, 79.595f, 42.302f, 79.6f)
                curveTo(42.325f, 79.621f, 42.351f, 79.641f, 42.37f, 79.65f)
                curveTo(42.444f, 79.688f, 42.538f, 79.709f, 42.63f, 79.725f)
                curveTo(42.686f, 79.735f, 42.742f, 79.743f, 42.798f, 79.75f)
                curveTo(42.654f, 79.694f, 42.51f, 79.637f, 42.366f, 79.582f)
                close()
                moveTo(39.568f, 29.455f)
                curveTo(38.148f, 29.5f, 36.905f, 29.714f, 35.469f, 30.11f)
                curveTo(34.627f, 30.342f, 33.871f, 30.68f, 33.072f, 30.966f)
                curveTo(32.445f, 31.337f, 31.751f, 31.671f, 31.19f, 32.081f)
                curveTo(29.93f, 33.002f, 29.199f, 33.924f, 28.522f, 35.05f)
                curveTo(27.365f, 36.973f, 26.832f, 38.993f, 26.769f, 41.041f)
                curveTo(26.779f, 41.792f, 26.744f, 42.543f, 26.797f, 43.293f)
                curveTo(27.096f, 47.514f, 28.573f, 51.678f, 30.744f, 55.674f)
                curveTo(31.211f, 56.463f, 31.651f, 57.259f, 32.145f, 58.041f)
                curveTo(34.512f, 61.791f, 37.468f, 65.404f, 41.025f, 68.776f)
                curveTo(41.616f, 69.315f, 42.194f, 69.86f, 42.798f, 70.394f)
                curveTo(44.906f, 72.255f, 47.221f, 74.033f, 49.799f, 75.654f)
                curveTo(50.475f, 76.069f, 51.161f, 76.477f, 51.859f, 76.876f)
                curveTo(51.924f, 76.845f, 51.988f, 76.814f, 52.053f, 76.782f)
                curveTo(55.877f, 74.899f, 59.761f, 73.034f, 63.047f, 70.781f)
                curveTo(64.58f, 69.727f, 65.147f, 69.361f, 66.608f, 68.261f)
                curveTo(68.998f, 66.461f, 71.167f, 64.547f, 72.976f, 62.502f)
                curveTo(74.153f, 61.117f, 74.549f, 60.72f, 75.54f, 59.275f)
                curveTo(77.168f, 56.9f, 78.303f, 54.408f, 79.162f, 51.886f)
                curveTo(79.681f, 50.127f, 79.919f, 49.496f, 80.234f, 47.708f)
                curveTo(80.486f, 46.273f, 80.642f, 44.779f, 80.579f, 43.334f)
                curveTo(80.494f, 41.375f, 80.272f, 40.868f, 79.8f, 38.944f)
                curveTo(79.53f, 38.272f, 79.323f, 37.588f, 78.989f, 36.927f)
                curveTo(78.388f, 35.737f, 77.797f, 34.925f, 76.714f, 33.907f)
                curveTo(76.317f, 33.534f, 75.881f, 33.176f, 75.409f, 32.838f)
                curveTo(75.195f, 32.685f, 74.7f, 32.452f, 74.317f, 32.261f)
                curveTo(73.132f, 32.181f, 72.02f, 32.35f, 70.906f, 32.677f)
                curveTo(70.404f, 32.825f, 69.941f, 33.019f, 69.459f, 33.19f)
                curveTo(68.953f, 33.432f, 68.419f, 33.653f, 67.941f, 33.916f)
                curveTo(65.276f, 35.387f, 63.336f, 37.322f, 61.732f, 39.274f)
                curveTo(61.243f, 39.869f, 60.817f, 40.483f, 60.36f, 41.087f)
                curveTo(59.987f, 41.691f, 59.587f, 42.29f, 59.24f, 42.901f)
                curveTo(58.052f, 44.995f, 57.247f, 47.171f, 56.674f, 49.36f)
                curveTo(56.026f, 51.098f, 56.491f, 50.52f, 52.134f, 48.646f)
                curveTo(50.308f, 48.259f, 49.826f, 48.296f, 50.193f, 47.855f)
                curveTo(50.112f, 47.799f, 50.337f, 47.683f, 50.376f, 47.596f)
                curveTo(50.611f, 47.083f, 50.816f, 46.422f, 50.92f, 45.916f)
                curveTo(51.021f, 45.423f, 51.07f, 44.926f, 51.146f, 44.432f)
                curveTo(51.203f, 42.721f, 51.321f, 41.957f, 50.953f, 40.191f)
                curveTo(50.409f, 37.578f, 49.201f, 34.961f, 47.04f, 32.674f)
                curveTo(46.348f, 31.942f, 45.46f, 31.289f, 44.67f, 30.596f)
                curveTo(45.323f, 30.66f, 46.96f, 31.142f, 46.628f, 30.788f)
                curveTo(46.101f, 30.228f, 44.9f, 30.032f, 43.922f, 29.799f)
                curveTo(43.016f, 29.584f, 42.029f, 29.506f, 41.061f, 29.466f)
                curveTo(40.535f, 29.444f, 40.041f, 29.44f, 39.568f, 29.455f)
                close()
                moveTo(43.307f, 79.949f)
                lineTo(48.181f, 82.004f)
                curveTo(48.195f, 81.984f, 48.214f, 81.956f, 48.235f, 81.916f)
                curveTo(46.59f, 81.262f, 44.957f, 80.597f, 43.307f, 79.949f)
                close()
            }
        }.build()

        return _favorites!!
    }

@Suppress("ObjectPropertyName")
private var _favorites: ImageVector? = null