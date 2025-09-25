package com.sergokuzneczow.selected_picture.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Tag
import com.sergokuzneczow.selected_picture.impl.SelectedPictureUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectedPictureScreen(
    selectedPictureUiState: SelectedPictureUiState,
    onTagChipClick: (Tag) -> Unit,
    onColorChipClick: (Color) -> Unit,
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState: SheetState = rememberModalBottomSheetState()
    var curtainVisible: Boolean by rememberSaveable { mutableStateOf(false) }
    var fabVisible: Boolean by rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (selectedPictureUiState) {
            is SelectedPictureUiState.Loading -> {
                PixelsCircularProgressIndicator()
            }

            is SelectedPictureUiState.Success -> {
                Curtain(curtainVisible = curtainVisible)
                SelectedPicture(
                    picturePath = selectedPictureUiState.picturePath,
                    onPictureClick = {
                        curtainVisible = !curtainVisible
                        fabVisible = !curtainVisible
                        if (curtainVisible) coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    },
                )
                PictureInformationFloatingActionButton(
                    fabVisible = fabVisible,
                    onFabClick = {
                        coroutineScope.launch {
                            curtainVisible = false
                            bottomSheetState.show()
                        }
                    },
                )
                if (bottomSheetState.isVisible) {
                    PictureInformationBottomSheet(
                        coroutineScope = coroutineScope,
                        bottomSheetState = bottomSheetState,
                        tags = selectedPictureUiState.tags,
                        colors = selectedPictureUiState.colors,
                        onTagChipClick = { tag: Tag -> onTagChipClick.invoke(tag) },
                        onColorChipClick = { color: com.sergokuzneczow.models.Color -> onColorChipClick.invoke(color) },
                    )
                }
            }
        }
    }
}