package com.sergokuzneczow.bottom_sheet_picture_info.impl.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.sergokuzneczow.bottom_sheet_picture_info.impl.ColorsListUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.LikeThisButtonUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.SavePictureButtonUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.TagsListUiState
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.system_components.buttons.PixelsSurfaceButton
import com.sergokuzneczow.core.system_components.chip_segments.PixelsSuggestionFlowRow
import com.sergokuzneczow.core.system_components.chip_segments.SuggestionChip
import com.sergokuzneczow.core.system_components.chip_segments.SuggestionChipColorsAccent
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.Tag
import com.sergokuzneczow.utilities.logger.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PictureInformationBottomSheet(
    pictureInfoUiState: PictureInformationUiState,
    onTagChipClick: (tag: Tag) -> Unit,
    onColorChipClick: (color: com.sergokuzneczow.models.Color) -> Unit,
    onSavePictureClick: (picturePath: String) -> Unit,
    onLikeThisPictureClick: (pictureKey: String) -> Unit,
    onDismissRequest: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                bottomSheetState.hide()
                onDismissRequest.invoke()
            }
        },
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        content = {
            when (pictureInfoUiState) {
                PictureInformationUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp),
                        content = { PixelsCircularProgressIndicator() },
                    )
                }

                is PictureInformationUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        PictureInformation(
                            tagsListUiState = pictureInfoUiState.tagsListUiState,
                            colorsListUiState = pictureInfoUiState.colorsListUiState,
                            onTagChipClick = onTagChipClick,
                            onColorChipClick = onColorChipClick,
                        )
                        PictureActions(
                            savePictureButtonUiState = pictureInfoUiState.savePictureButtonUiState,
                            likeThisButtonUiState = pictureInfoUiState.likeThisButtonUiState,
                            onSavePictureClick = onSavePictureClick,
                            onLikeThisPictureClick = onLikeThisPictureClick,
                        )
                        Spacer(modifier = Modifier.height(Dimensions.LargePadding))
                    }
                }
            }
        }
    )
}

@Composable
private fun PictureActions(
    savePictureButtonUiState: SavePictureButtonUiState,
    likeThisButtonUiState: LikeThisButtonUiState,
    onSavePictureClick: (picturePath: String) -> Unit,
    onLikeThisPictureClick: (pictureKeu: String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Padding)
    ) {
        SavePictureButton(
            savePictureButtonUiState = savePictureButtonUiState,
            onSavePictureClick = onSavePictureClick,
        )
        LikeThisPictureButton(
            likeThisButtonUiState = likeThisButtonUiState,
            onLikeThisPictureClick = onLikeThisPictureClick,
        )
    }
}

@Composable
private fun PictureInformation(
    tagsListUiState: TagsListUiState,
    colorsListUiState: ColorsListUiState,
    onTagChipClick: (tag: Tag) -> Unit,
    onColorChipClick: (color: com.sergokuzneczow.models.Color) -> Unit,
) {
    when (tagsListUiState) {
        TagsListUiState.Empty -> {}

        TagsListUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                content = { PixelsCircularProgressIndicator() }
            )
        }

        is TagsListUiState.Success -> {
            val tagsSuggestionChips: List<SuggestionChip> = tagsListUiState.tags.map { SuggestionChip(label = "#${it.name}") }

            Text(
                text = "Tags:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )

            PixelsSuggestionFlowRow(
                suggestionChips = tagsSuggestionChips,
                modifier = Modifier.padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
                onItemClick = { index, _ -> onTagChipClick.invoke(tagsListUiState.tags[index]) },
                colorAccentPredicate = { index, _ ->
                    when (tagsListUiState.tags[index].purity) {
                        Tag.TagPurity.SFW -> SuggestionChipColorsAccent.Standard
                        Tag.TagPurity.SKETCHY -> SuggestionChipColorsAccent.Warning
                        Tag.TagPurity.NSFW -> SuggestionChipColorsAccent.Dangerous
                    }
                },
            )

        }
    }

    when (colorsListUiState) {
        ColorsListUiState.Empty -> {
        }

        ColorsListUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                content = { PixelsCircularProgressIndicator() }
            )
        }

        is ColorsListUiState.Success -> {
            val colorsSuggestionChips: List<SuggestionChip> = colorsListUiState.colors.map { SuggestionChip(label = it.name) }
            if (colorsListUiState.colors.isNotEmpty()) {
                Text(
                    text = "Colors:",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )

                PixelsSuggestionFlowRow(
                    suggestionChips = colorsSuggestionChips,
                    modifier = Modifier.padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
                    onItemClick = { index, _ -> onColorChipClick.invoke(colorsListUiState.colors[index]) },
                    colorAccentPredicate = { index, _ ->
                        SuggestionChipColorsAccent.Custom(
                            containerColor = Color(colorsListUiState.colors[index].name.toColorInt()),
                            contentColor = Color(colorsListUiState.colors[index].name.toColorInt()),
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun RowScope.SavePictureButton(
    savePictureButtonUiState: SavePictureButtonUiState,
    onSavePictureClick: (String) -> Unit,
) {
    log(tag = "SavePictureButton") { "savePictureButtonUiState=$savePictureButtonUiState" }

    var isVisibleProgressBar: Boolean by rememberSaveable { mutableStateOf(false) }
    var isEnable: Boolean by rememberSaveable { mutableStateOf(false) }

    when (savePictureButtonUiState) {
        is SavePictureButtonUiState.Prepared -> {
            isVisibleProgressBar = false
            isEnable = true
        }

        is SavePictureButtonUiState.Loading -> {
            isVisibleProgressBar = true
            isEnable = false
        }

        is SavePictureButtonUiState.Saved -> {
            isVisibleProgressBar = false
            isEnable = true
        }
    }

    PixelsSurfaceButton(
        text = "Save",
        onClick = { onSavePictureClick.invoke(savePictureButtonUiState.picturePath) },
        enabled = isEnable,
        iconContent = {
            AnimatedVisibility(
                visible = isVisibleProgressBar,
                enter = fadeIn(),
                exit = fadeOut(),
                content = { PixelsCircularProgressIndicator(modifier = Modifier.size(16.dp)) }
            )
        },
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = Dimensions.ContentPadding)
    )
}

@Composable
private fun RowScope.LikeThisPictureButton(
    likeThisButtonUiState: LikeThisButtonUiState,
    onLikeThisPictureClick: (String) -> Unit,
) {
    var isEnable: Boolean by rememberSaveable { mutableStateOf(false) }

    isEnable = when (likeThisButtonUiState) {
        LikeThisButtonUiState.Empty -> false
        LikeThisButtonUiState.Loading -> false
        is LikeThisButtonUiState.Success -> true
    }

    PixelsSurfaceButton(
        text = "Like this",
        onClick = { if (likeThisButtonUiState is LikeThisButtonUiState.Success) onLikeThisPictureClick.invoke(likeThisButtonUiState.pictureKey) },
        enabled = isEnable,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = Dimensions.ContentPadding)
    )
}