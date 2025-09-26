package com.sergokuzneczow.suitable_pictures.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.suitable_pictures.impl.ui.SuitablePicturesScreen

@Composable
internal fun SuitablePicturesRootScreen(
    pageKey: Long,
    navigateToDialogPageFilterDestination: (pageKey: Long) -> Unit,
    navigateToSelectedPictureDestination: (pictureKey: String) -> Unit,
) {
    val vm: SuitablePicturesViewModel = viewModel(factory = SuitablePicturesViewModel.Factory(pageKey, LocalContext.current))
    val titleUiState: TitleUiState by vm.getTitleUiState().collectAsStateWithLifecycle()
    val suitablePicturesUiState: SuitablePicturesUiState by vm.getSuitablePicturesUiState().collectAsStateWithLifecycle()
    SuitablePicturesScreen(
        pageKey = pageKey,
        titleUiState = titleUiState,
        suitablePicturesUiState = suitablePicturesUiState,
        nextPage = vm::nextPage,
        navigateToDialogPageFilterDestination = navigateToDialogPageFilterDestination,
        navigateToSelectedPictureDestination = navigateToSelectedPictureDestination,
    )
}