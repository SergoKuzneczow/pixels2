package com.sergokuzneczow.selected_picture.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.selected_picture.impl.SelectedPictureUiState
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun SelectedPictureScreen(
    coroutineScope: CoroutineScope,
    uiState: SelectedPictureUiState,
    changeCurtainVisible: (isVisible: Boolean?) -> Unit,
    navigateToBottomSheetPictureInfoDestination: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is SelectedPictureUiState.Loading -> PixelsProgressIndicator()

            is SelectedPictureUiState.Success -> {
                Curtain(curtainVisible = uiState.curtainVisible)
                PictureContainer(
                    coroutineScope = coroutineScope,
                    picturePath = uiState.picturePath,
                    onPictureClick = { changeCurtainVisible.invoke(null) },
                    onPictureZoomClick = { isZoomed -> changeCurtainVisible.invoke(isZoomed) }
                )
                PictureInformationFloatingActionButton(
                    fabVisible = uiState.infoFabVisible,
                    onFabClick = { navigateToBottomSheetPictureInfoDestination.invoke(uiState.pictureKey) },
                )
            }
        }
    }
}