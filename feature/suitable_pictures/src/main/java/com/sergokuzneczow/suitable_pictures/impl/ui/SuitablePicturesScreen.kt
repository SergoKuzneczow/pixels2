package com.sergokuzneczow.suitable_pictures.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.PixelsPrimaryFloatingActionButton
import com.sergokuzneczow.core.system_components.PixelsTopBar
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesUiState
import com.sergokuzneczow.suitable_pictures.impl.TitleUiState
import com.sergokuzneczow.utilities.logger.log

@Composable
internal fun SuitablePicturesScreen(
    pageKey: Long,
    titleUiState: TitleUiState,
    suitablePicturesUiState: SuitablePicturesUiState,
    nextPage: () -> Unit,
    navigateToDialogPageFilterDestination: (pageKey: Long) -> Unit,
    navigateToSelectedPictureDestination: (pictureKey: String) -> Unit,
) {
    log(tag = "SuitablePicturesScreen") { "suitablePicturesUiState=$suitablePicturesUiState" }
    Box(modifier = Modifier.fillMaxSize()) {
        SuitablePicturesList(
            suitablePicturesListUiState = suitablePicturesUiState,
            onItemClick = { navigateToSelectedPictureDestination.invoke(it) },
            nextPage = nextPage,
        )
        PixelsTopBar(
            title = titleUiState.title,
            visibleProgressBar = false,
            modifier = Modifier
        )
        PixelsPrimaryFloatingActionButton(
            imageVector = PixelsIcons.filter,
            onCLick = { navigateToDialogPageFilterDestination.invoke(pageKey) }
        )
    }
}