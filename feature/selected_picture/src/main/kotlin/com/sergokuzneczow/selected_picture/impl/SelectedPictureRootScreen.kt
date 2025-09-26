package com.sergokuzneczow.selected_picture.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.selected_picture.impl.ui.SelectedPictureScreen

@Composable
internal fun SelectedPictureRootScreen(
    pictureKey: String,
    navigateToBottomSheetPictureInfoDestination: (pictureKey: String) -> Unit,
) {
    val vm: SelectedPictureViewModel = viewModel(factory = SelectedPictureViewModel.Factory(LocalContext.current, pictureKey))
    val selectedPictureUiState: SelectedPictureUiState by vm.getSelectedPictureUiState().collectAsStateWithLifecycle()
    SelectedPictureScreen(
        selectedPictureUiState = selectedPictureUiState,
        navigateToBottomSheetPictureInfoDestination = navigateToBottomSheetPictureInfoDestination,
    )
}