package com.sergokuzneczow.bottom_sheet_picture_info.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.bottom_sheet_picture_info.impl.ColorsListUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.LikeThisButtonUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.SavePictureButtonUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.TagsListUiState
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.Tag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetPictureInfoScreen(
    pictureInfoUiState: PictureInformationUiState,
    savePicture: (picturePath: String) -> Unit,
    searchLikeThisPicture: (pictureKey: String) -> Unit,
    onTagChipClick: (tag: Tag) -> Unit,
    onColorChipClick: (color: Color) -> Unit,
    popBackStack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        PictureInformationBottomSheet(
            pictureInfoUiState = pictureInfoUiState,
            onTagChipClick = { tag: Tag -> onTagChipClick.invoke(tag) },
            onColorChipClick = { color: Color -> onColorChipClick.invoke(color) },
            onSavePictureClick = { picturePath: String -> savePicture.invoke(picturePath) },
            onLikeThisPictureButtonClick = { pictureKey: String -> searchLikeThisPicture.invoke(pictureKey) },
            whenDismissRequest = { popBackStack.invoke() },
        )
    }
}

@ThemePreviews
@Composable
private fun BottomSheetPictureInfoScreenLoadingPreview() {
    PixelsTheme {
        BottomSheetPictureInfoScreen(
            pictureInfoUiState = PictureInformationUiState.Loading,
            onTagChipClick = {},
            onColorChipClick = {},
            savePicture = {},
            searchLikeThisPicture = {},
            popBackStack = {},
        )
    }
}

@ThemePreviews
@Composable
private fun BottomSheetPictureInfoScreenSuccessPreview() {
    PixelsTheme {
        BottomSheetPictureInfoScreen(
            pictureInfoUiState = PictureInformationUiState.Success(
                savePictureButtonUiState = SavePictureButtonUiState.Prepared(""),
                likeThisButtonUiState = LikeThisButtonUiState.Success(""),
                tagsListUiState = TagsListUiState.Success(
                    listOf(
                        Tag(
                            id = 1,
                            name = "Tag name",
                            alias = "1",
                            categoryId = 0,
                            categoryName = "1",
                            purity = Tag.TagPurity.SFW,
                            createdAt = "1",
                        )
                    )
                ),
                colorsListUiState = ColorsListUiState.Success(
                    listOf(
                        Color(
                            key = "#660000",
                            name = "#660000"
                        )
                    )
                )
            ),
            onTagChipClick = {},
            onColorChipClick = {},
            savePicture = {},
            searchLikeThisPicture = {},
            popBackStack = {},
        )
    }
}