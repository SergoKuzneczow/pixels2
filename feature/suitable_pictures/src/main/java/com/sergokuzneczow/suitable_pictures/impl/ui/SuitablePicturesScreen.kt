package com.sergokuzneczow.suitable_pictures.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
internal fun SuitablePicturesScreen(
    suitablePicturesUiState: State<SuitablePicturesUiState>,
    nextPage: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SuitablePicturesList(
            suitablePicturesUiState = suitablePicturesUiState.value,
            onItemClick = {},
            nextPage = nextPage,
        )
    }
}