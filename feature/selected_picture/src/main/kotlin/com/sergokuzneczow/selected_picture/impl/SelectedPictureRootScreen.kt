package com.sergokuzneczow.selected_picture.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.selected_picture.impl.SelectedPictureIntent.CHANGE_VISIBLE_CURTAIN
import com.sergokuzneczow.selected_picture.impl.SelectedPictureIntent.HIDE_CURTAIN
import com.sergokuzneczow.selected_picture.impl.SelectedPictureIntent.VISIBLE_CURTAIN
import com.sergokuzneczow.selected_picture.impl.ui.SelectedPictureScreen
import com.sergokuzneczow.selected_picture.impl.view_model.SelectedPictureViewModel
import com.sergokuzneczow.selected_picture.impl.view_model.SelectedPictureViewModelFactory
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun SelectedPictureRootScreen(
    pictureKey: String,
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    navigateToBottomSheetPictureInfoDestination: (pictureKey: String) -> Unit,
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val vm: SelectedPictureViewModel = viewModel(factory = SelectedPictureViewModelFactory(LocalContext.current, pictureKey))
    val uiState: SelectedPictureUiState by vm.uiState.collectAsStateWithLifecycle()

    SelectedPictureScreen(
        coroutineScope = coroutineScope,
        uiState = uiState,
        changeCurtainVisible = { isVisible: Boolean? ->
            when (isVisible) {
                true -> vm.setIntent(VISIBLE_CURTAIN)
                false -> vm.setIntent(HIDE_CURTAIN)
                null -> vm.setIntent(CHANGE_VISIBLE_CURTAIN)
            }
        },
        navigateToBottomSheetPictureInfoDestination = navigateToBottomSheetPictureInfoDestination,
    )

    LaunchedEffect(uiState) {
        uiState.exceptionMessage?.let { /*onShowSnackbar.invoke(it, null)*/ }
    }
}