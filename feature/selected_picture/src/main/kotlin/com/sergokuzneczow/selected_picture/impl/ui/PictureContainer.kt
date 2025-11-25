package com.sergokuzneczow.selected_picture.impl.ui

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val MAX_ZOOM: Float = 10f
private const val DOUBLE_CLICK_ZOOM: Float = 3f

@Composable
internal fun BoxScope.PictureContainer(
    coroutineScope: CoroutineScope,
    picturePath: String,
    onPictureClick: () -> Unit,
    onPictureZoomClick: (isZoomed: Boolean) -> Unit,
) {
    var loadSuccess: Boolean by rememberSaveable { mutableStateOf(false) }
    val painter: AsyncImagePainter = rememberAsyncImagePainter(picturePath)
    val state: AsyncImagePainter.State by painter.state.collectAsStateWithLifecycle()

    when (state) {
        is AsyncImagePainter.State.Empty -> loadSuccess = false
        is AsyncImagePainter.State.Loading -> loadSuccess = false
        is AsyncImagePainter.State.Success -> loadSuccess = true
        is AsyncImagePainter.State.Error -> painter.restart()
    }

    when (loadSuccess) {
        true -> {
            PictureView(
                coroutineScope = coroutineScope,
                painter = painter,
                picturePath = picturePath,
                onPictureClick = onPictureClick,
                onPictureZoomClick = onPictureZoomClick,
            )
        }

        false -> PixelsProgressIndicator()
    }
}

@Composable
private fun BoxScope.PictureView(
    coroutineScope: CoroutineScope,
    painter: AsyncImagePainter,
    picturePath: String,
    onPictureClick: () -> Unit,
    onPictureZoomClick: (isZoomed: Boolean) -> Unit,
) {
    val zoom: MutableFloatState = remember { mutableFloatStateOf(1f) }
    val offset: MutableState<Offset> = remember { mutableStateOf(Offset.Zero) }
    val containerSize: MutableState<IntSize> = remember { mutableStateOf(IntSize.Zero) }
    val pictureSize: MutableState<IntSize> = remember { mutableStateOf(IntSize.Zero) }

    val transformableState: TransformableState = pictureTransformableState(
        offset,
        zoom,
        containerSize,
        pictureSize,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)
            .onSizeChanged { containerSize.value = it }
            .transformable(transformableState)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onPictureClick.invoke() },
                    onDoubleTap = {
                        when (zoom.floatValue) {
                            1f -> coroutineScope.animateZoomTransformableState(transformableState, current = zoom.floatValue, new = DOUBLE_CLICK_ZOOM)
                            else -> coroutineScope.animateZoomTransformableState(transformableState, current = zoom.floatValue, new = 1f)
                        }
                    })
            }
    ) {
        Image(
            painter = painter,
            contentDescription = picturePath,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .onSizeChanged { pictureSize.value = it }
                .graphicsLayer {
                    translationX = offset.value.x
                    translationY = offset.value.y
                    scaleX = zoom.floatValue.apply { if (this > 1f) onPictureZoomClick.invoke(true) else onPictureZoomClick.invoke(false) }
                    scaleY = zoom.floatValue
                }
        )
    }
}

@Composable
private fun pictureTransformableState(
    offset: MutableState<Offset>,
    zoom: MutableFloatState,
    containerSize: MutableState<IntSize>,
    pictureSize: MutableState<IntSize>,
): TransformableState = rememberTransformableState { zoomChange: Float, panChange: Offset, _: Float ->
    val newScale = (zoom.floatValue * zoomChange).coerceIn(1f, MAX_ZOOM)
    val newOffset = offset.value + panChange

    zoom.floatValue = newScale

    val maxX: Float = if (pictureSize.value.width * zoom.floatValue > containerSize.value.width) (pictureSize.value.width * zoom.value - containerSize.value.width) / 2f else 0f
    val maxY: Float = if (pictureSize.value.height * zoom.floatValue > containerSize.value.height) (pictureSize.value.height * zoom.value - containerSize.value.height) / 2f else 0f

    offset.value = Offset(
        newOffset.x.coerceIn(-maxX, maxX),
        newOffset.y.coerceIn(-maxY, maxY)
    )
}

private fun CoroutineScope.animateZoomTransformableState(
    transformableState: TransformableState,
    current: Float,
    new: Float,
) {
    this.launch {
        var previous = current
        AnimationState(initialValue = previous).animateTo(new, SpringSpec(stiffness = Spring.StiffnessLow)) {
            val scaleFactor: Float = if (previous == 0f) 1f else this.value / previous
            this@animateZoomTransformableState.launch {
                transformableState.transform { transformBy(zoomChange = scaleFactor) }
            }
            previous = this.value
        }
    }
}