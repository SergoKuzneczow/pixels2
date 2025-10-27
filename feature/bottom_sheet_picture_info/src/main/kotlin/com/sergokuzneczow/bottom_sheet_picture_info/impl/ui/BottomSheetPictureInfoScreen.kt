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
    onSavePictureClick: (picturePath: String) -> Unit,
    onLikeThisPictureClick: (pictureKey: String) -> Unit,
    onTagChipClick: (tag: Tag) -> Unit,
    onColorChipClick: (color: Color) -> Unit,
    popBackStack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        PictureInformationBottomSheet(
            pictureInfoUiState = pictureInfoUiState,
            onTagChipClick = onTagChipClick,
            onColorChipClick = onColorChipClick,
            onSavePictureClick = onSavePictureClick,
            onLikeThisPictureClick = onLikeThisPictureClick,
            onDismissRequest = { popBackStack.invoke() },
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
            onSavePictureClick = {},
            onLikeThisPictureClick = {},
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
            onSavePictureClick = {},
            onLikeThisPictureClick = {},
            popBackStack = {},
        )
    }
}