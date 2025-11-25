package com.sergokuzneczow.selected_picture.impl.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator

@Composable
internal fun BoxScope.SelectedPicture(
    picturePath: String,
    onPictureClick: () -> Unit,
) {
    var pictureVisible: Boolean by rememberSaveable { mutableStateOf(false) }
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
            AnimatedVisibility(
                visible = pictureVisible,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Image(
                    painter = painter,
                    contentDescription = picturePath,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .clickable(onClick = { onPictureClick.invoke() })
                )
            }
            pictureVisible = true
        }

        false -> PixelsProgressIndicator()
    }
}