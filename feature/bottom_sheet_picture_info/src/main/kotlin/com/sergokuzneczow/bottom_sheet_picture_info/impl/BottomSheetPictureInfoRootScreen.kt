package com.sergokuzneczow.bottom_sheet_picture_info.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.bottom_sheet_picture_info.impl.ui.BottomSheetPictureInfoScreen
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Tag

@Composable
internal fun BottomSheetPictureInfoRootScreen(
    pictureKey: String,
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
    popBackStack: () -> Unit
) {
    val vm: BottomSheetPictureInfoViewModel = viewModel(factory = BottomSheetPictureInfoViewModel.Factory(LocalContext.current, pictureKey))
    val pictureInformationUiState: PictureInformationUiState by vm.getPictureInformationUiState().collectAsStateWithLifecycle()

    BottomSheetPictureInfoScreen(
        pictureInformationUiState = pictureInformationUiState,
        savePicture = vm::savePicture,
        searchLikeThisPicture = { pictureKey ->
            vm.getPageKey(
                pageQuery = PageQuery.Like(pictureKey = pictureKey, description = pictureKey),
                pageFilter = PageFilter.DEFAULT,
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
        },
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
        popBackStack = popBackStack,
    )
}