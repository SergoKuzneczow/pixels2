package com.sergokuzneczow.selected_picture.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsCircularProgressIndicator
import com.sergokuzneczow.selected_picture.impl.SelectedPictureUiState

@Composable
internal fun SelectedPictureScreen(
    uiState: SelectedPictureUiState,
    changeCurtainVisible: () -> Unit,
    navigateToBottomSheetPictureInfoDestination: (String) -> Unit,
) {
//    var curtainVisible: Boolean by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is SelectedPictureUiState.Loading -> {
                PixelsCircularProgressIndicator()
            }

            is SelectedPictureUiState.Success -> {
                Curtain(curtainVisible = uiState.curtainVisible)
                SelectedPicture(
                    picturePath = uiState.picturePath,
                    onPictureClick = { changeCurtainVisible.invoke() },
                )
                PictureInformationFloatingActionButton(
                    fabVisible = uiState.infoFabVisible,
                    onFabClick = { navigateToBottomSheetPictureInfoDestination.invoke(uiState.pictureKey) },
                )
            }
        }
    }
}