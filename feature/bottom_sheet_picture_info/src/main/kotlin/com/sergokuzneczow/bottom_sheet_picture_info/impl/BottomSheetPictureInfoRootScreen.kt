package com.sergokuzneczow.bottom_sheet_picture_info.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.bottom_sheet_picture_info.PictureInformationIntent
import com.sergokuzneczow.bottom_sheet_picture_info.impl.ui.BottomSheetPictureInfoScreen
import com.sergokuzneczow.bottom_sheet_picture_info.impl.view_model.BottomSheetPictureInfoViewModel
import com.sergokuzneczow.bottom_sheet_picture_info.impl.view_model.BottomSheetPictureInfoViewModelFactory
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
    val vm: BottomSheetPictureInfoViewModel = viewModel(factory = BottomSheetPictureInfoViewModelFactory(LocalContext.current, pictureKey))
    val pictureInfoUiState: PictureInformationUiState by vm.uiState.collectAsStateWithLifecycle()

    BottomSheetPictureInfoScreen(
        pictureInfoUiState = pictureInfoUiState,
        savePicture = { picturePath: String ->
            vm.setIntent(PictureInformationIntent.SavingPicture(picturePath))
        },
        searchLikeThisPicture = { pictureKey ->
            vm.setIntent(
                PictureInformationIntent.SearchPageKey(
                    pageQuery = PageQuery.Like(pictureKey = pictureKey, description = pictureKey),
                    pageFilter = PageFilter.DEFAULT,
                    completedBlock = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
            )
        },
        onTagChipClick = { tag: Tag ->
            vm.setIntent(
                PictureInformationIntent.SearchPageKey(
                    pageQuery = PageQuery.Tag(tagKey = tag.id, description = tag.name),
                    pageFilter = PageFilter.DEFAULT,
                    completedBlock = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
            )
        },
        onColorChipClick = { color: Color ->
            vm.setIntent(
                PictureInformationIntent.SearchPageKey(
                    pageQuery = PageQuery.DEFAULT,
                    pageFilter = PageFilter.DEFAULT.copy(pictureColor = PageFilter.PictureColor(colorName = color.name)),
                    completedBlock = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
            )
        },
        popBackStack = popBackStack,
    )
}