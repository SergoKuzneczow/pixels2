package com.sergokuzneczow.selected_picture.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.selected_picture.impl.SelectedPictureUiState

@Composable
internal fun SelectedPictureScreen(
    selectedPictureUiState: SelectedPictureUiState,
    navigateToBottomSheetPictureInfoDestination: (String) -> Unit,
) {
    var curtainVisible: Boolean by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (selectedPictureUiState) {
            is SelectedPictureUiState.Loading -> {
                PixelsCircularProgressIndicator()
            }

            is SelectedPictureUiState.Success -> {
                Curtain(curtainVisible = curtainVisible)
                SelectedPicture(
                    picturePath = selectedPictureUiState.picturePath,
                    onPictureClick = { curtainVisible = !curtainVisible },
                )
                PictureInformationFloatingActionButton(
                    fabVisible = !curtainVisible,
                    onFabClick = { navigateToBottomSheetPictureInfoDestination.invoke(selectedPictureUiState.pictureKey) },
                )
            }
        }
    }
}