package com.sergokuzneczow.suitable_pictures.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.system_components.PixelsPrimaryFloatingActionButton
import com.sergokuzneczow.core.system_components.PixelsTopBar
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.suitable_pictures.R.string.empty_collection
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesUiState
import com.sergokuzneczow.suitable_pictures.impl.TitleUiState

@Composable
internal fun SuitablePicturesScreen(
    pageKey: Long,
    titleUiState: TitleUiState,
    suitablePicturesUiState: SuitablePicturesUiState,
    nextPage: () -> Unit,
    navigateToDialogPageFilterDestination: (pageKey: Long) -> Unit,
    navigateToSelectedPictureDestination: (pictureKey: String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (suitablePicturesUiState) {
            is SuitablePicturesUiState.Empty -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(empty_collection),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is SuitablePicturesUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    PixelsCircularProgressIndicator()
                }
            }

            is SuitablePicturesUiState.Success -> {
                SuitablePicturesList(
                    uiStateSuccess = suitablePicturesUiState,
                    onItemClick = { navigateToSelectedPictureDestination.invoke(it) },
                    nextPage = nextPage,
                )
                PixelsPrimaryFloatingActionButton(
                    imageVector = PixelsIcons.filter,
                    onCLick = { navigateToDialogPageFilterDestination.invoke(pageKey) }
                )
            }
        }
        PixelsTopBar(
            title = titleUiState.title,
            visibleProgressBar = false,
            modifier = Modifier
        )
    }
}