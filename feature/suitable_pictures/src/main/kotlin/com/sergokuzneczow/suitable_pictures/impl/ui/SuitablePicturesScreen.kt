package com.sergokuzneczow.suitable_pictures.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil3.ImageLoader
import com.sergokuzneczow.core.system_components.PixelsPrimaryFloatingActionButton
import com.sergokuzneczow.core.system_components.PixelsTopBar
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.suitable_pictures.R
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesState
import com.sergokuzneczow.utilities.log

@Composable
internal fun SuitablePicturesScreen(
    state: SuitablePicturesState,
    imageLoader: ImageLoader,
    nextPage: () -> Unit,
    onBackMainMenu: () -> Unit,
    onPictureClick: (pictureKey: String) -> Unit,
    onFilterButtonClick: () -> Unit,
) {
    log { state.toString() }
    when (state) {
        is SuitablePicturesState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                PixelsTopBar(
                    title = state.title,
                    onHomeIconClick = { onBackMainMenu.invoke() }
                )
                PixelsProgressIndicator()
            }
        }

        is SuitablePicturesState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                SuitablePicturesList(
                    stateSuccess = state,
                    imageLoader = imageLoader,
                    onPictureClick = onPictureClick,
                    nextPage = nextPage,
                )
                PixelsPrimaryFloatingActionButton(
                    imageVector = PixelsIcons.filter,
                    onClick = { onFilterButtonClick.invoke() }
                )
                PixelsTopBar(
                    title = state.title,
                    onHomeIconClick = { onBackMainMenu.invoke() }
                )
            }
        }

        is SuitablePicturesState.Empty -> {
            Box(modifier = Modifier.fillMaxSize()) {
                PixelsTopBar(
                    title = state.title,
                    onHomeIconClick = { onBackMainMenu.invoke() }
                )
                Text(
                    text = stringResource(R.string.empty_collection),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.Center)
                )
                PixelsPrimaryFloatingActionButton(
                    imageVector = PixelsIcons.filter,
                    onClick = { onFilterButtonClick.invoke() }
                )
            }
        }
    }
}