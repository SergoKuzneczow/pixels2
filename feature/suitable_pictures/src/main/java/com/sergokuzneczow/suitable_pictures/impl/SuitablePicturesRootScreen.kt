package com.sergokuzneczow.suitable_pictures.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.suitable_pictures.impl.ui.SuitablePicturesScreen

@Composable
internal fun SuitablePicturesRootScreen(
    pageKey: Long,
    titleState: MutableState<String>,
    showProgressBar: (visible: Boolean) -> Unit,
) {
    val vm: SuitablePicturesViewModel = viewModel(factory = SuitablePicturesViewModel.Factory(pageKey, LocalContext.current))
    titleState.value = vm.getTitleUiState().collectAsState().value.title
    SuitablePicturesScreen(
        suitablePicturesUiState = vm.getSuitablePicturesUiState().collectAsState(),
        nextPage = vm::nextPage
    )
}