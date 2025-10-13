package com.sergokuzneczow.selected_picture.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.selected_picture.impl.SelectedPictureIntent.CHANGE_VISIBLE_CURTAIN
import com.sergokuzneczow.selected_picture.impl.ui.SelectedPictureScreen

@Composable
internal fun SelectedPictureRootScreen(
    pictureKey: String,
    navigateToBottomSheetPictureInfoDestination: (pictureKey: String) -> Unit,
) {
    val vm: SelectedPictureViewModel = viewModel(factory = SelectedPictureViewModel.Factory(LocalContext.current, pictureKey))
    val uiState: SelectedPictureUiState by vm.uiState.collectAsStateWithLifecycle()
    SelectedPictureScreen(
        uiState = uiState,
        changeCurtainVisible = { vm.setIntent(CHANGE_VISIBLE_CURTAIN) },
        navigateToBottomSheetPictureInfoDestination = navigateToBottomSheetPictureInfoDestination,
    )
}