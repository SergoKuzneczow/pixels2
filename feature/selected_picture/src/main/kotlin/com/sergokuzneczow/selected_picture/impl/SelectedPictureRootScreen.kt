package com.sergokuzneczow.selected_picture.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Tag
import com.sergokuzneczow.selected_picture.impl.ui.SelectedPictureScreen

@Composable
internal fun SelectedPictureRootScreen(
    pictureKey: String,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    val vm: SelectedPictureViewModel = viewModel(factory = SelectedPictureViewModel.Factory(LocalContext.current, pictureKey))
    val selectedPictureUiState: SelectedPictureUiState by vm.getSelectedPictureUiState().collectAsStateWithLifecycle()
    SelectedPictureScreen(
        selectedPictureUiState = selectedPictureUiState,
        onTagChipClick = { tag: Tag ->
            vm.getPageKey(
                pageQuery = PageQuery.Tag(tagKey = tag.id, description = tag.name),
                pageFilter = PageFilter.DEFAULT,
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
        },
        onColorChipClick = { color: Color ->
            vm.getPageKey(
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureColor = PageFilter.PictureColor(colorName = color.name)),
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
        },
    )
}