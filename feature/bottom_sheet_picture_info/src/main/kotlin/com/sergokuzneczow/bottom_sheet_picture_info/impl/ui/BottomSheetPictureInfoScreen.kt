package com.sergokuzneczow.bottom_sheet_picture_info.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationUiState
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Tag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetPictureInfoScreen(
    pictureInformationUiState: PictureInformationUiState,
    savePicture: (String) -> Unit,
    searchLikeThisPicture: (String) -> Unit,
    onTagChipClick: (Tag) -> Unit,
    onColorChipClick: (Color) -> Unit,
    popBackStack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (pictureInformationUiState) {
            is PictureInformationUiState.Loading -> {
                PixelsCircularProgressIndicator()
            }

            is PictureInformationUiState.Success -> {
                PictureInformationBottomSheet(
                    picturePath = pictureInformationUiState.picturePath,
                    tags = pictureInformationUiState.tags,
                    colors = pictureInformationUiState.colors,
                    saveButtonUiState = pictureInformationUiState.pictureSavingUiState,
                    onTagChipClick = { tag: Tag -> onTagChipClick.invoke(tag) },
                    onColorChipClick = { color: Color -> onColorChipClick.invoke(color) },
                    onSavePictureClick = { savePicture.invoke(pictureInformationUiState.picturePath) },
                    onLikeThisPictureButtonClick = { searchLikeThisPicture.invoke(pictureInformationUiState.pictureKey) },
                    whenDismissRequest = { popBackStack.invoke() }
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun BottomSheetPictureInfoScreenPreview() {
    PixelsTheme {
        BottomSheetPictureInfoScreen(
            pictureInformationUiState = PictureInformationUiState.Success(
                pictureKey = "preview",
                picturePath = "preview",
                tags = listOf(
                    Tag(
                        id = 0,
                        name = "Preview_tag_name",
                        alias = "",
                        categoryId = 1,
                        categoryName = "",
                        purity = Tag.TagPurity.SFW,
                        createdAt = ""
                    )
                ),
                colors = listOf(
                    Color(
                        key = "#660000",
                        name = "#660000"
                    )
                ),
                pictureSavingUiState = PictureInformationUiState.PictureSavingUiState.Prepared,
            ),
            onTagChipClick = {},
            onColorChipClick = {},
            savePicture = {},
            searchLikeThisPicture = {},
            popBackStack = {},
        )
    }
}