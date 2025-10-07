package com.sergokuzneczow.bottom_sheet_picture_info.impl.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationUiState
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.system_components.buttons.PixelsSurfaceButton
import com.sergokuzneczow.core.system_components.chip_segments.PixelsSuggestionFlowRow
import com.sergokuzneczow.core.system_components.chip_segments.SuggestionChip
import com.sergokuzneczow.core.system_components.chip_segments.SuggestionChipColorsAccent
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PictureInformationBottomSheet(
    picturePath: String,
    tags: List<Tag>,
    colors: List<com.sergokuzneczow.models.Color>,
    saveButtonUiState: PictureInformationUiState.PictureSavingUiState,
    onTagChipClick: (tag: Tag) -> Unit,
    onColorChipClick: (color: com.sergokuzneczow.models.Color) -> Unit,
    onSavePictureClick: (picturePath: String) -> Unit,
    onLikeThisPictureButtonClick: () -> Unit,
    whenDismissRequest: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                bottomSheetState.hide()
                whenDismissRequest.invoke()
            }
        },
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                PictureInformation(
                    tags = tags,
                    colors = colors,
                    onTagChipClick = onTagChipClick,
                    onColorChipClick = onColorChipClick,
                )
                PictureActions(
                    picturePath = picturePath,
                    saveButtonState = saveButtonUiState,
                    onSavePictureClick = onSavePictureClick,
                    onLikeThisPictureClick = onLikeThisPictureButtonClick,
                )
                Spacer(modifier = Modifier.height(Dimensions.LargePadding))
            }
        }
    )
}

@Composable
private fun PictureActions(
    picturePath: String,
    saveButtonState: PictureInformationUiState.PictureSavingUiState,
    onSavePictureClick: (picturePath: String) -> Unit,
    onLikeThisPictureClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Padding)
    ) {
        SavePictureButton(
            saveButtonState = saveButtonState,
            onSavePictureClick = { onSavePictureClick.invoke(picturePath) },
        )
        PixelsSurfaceButton(
            text = "Like this",
            onClick = { onLikeThisPictureClick.invoke() },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimensions.ContentPadding)
        )
    }
}

@Composable
private fun PictureInformation(
    tags: List<Tag>,
    colors: List<com.sergokuzneczow.models.Color>,
    onTagChipClick: (tag: Tag) -> Unit,
    onColorChipClick: (color: com.sergokuzneczow.models.Color) -> Unit,
) {
    val tagsSuggestionChips: List<SuggestionChip> = tags.map { SuggestionChip(label = "#${it.name}") }
    val colorsSuggestionChips: List<SuggestionChip> = colors.map { SuggestionChip(label = it.name) }

    if (tags.isNotEmpty()) {
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
            onItemClick = { index, _ -> onTagChipClick.invoke(tags[index]) },
            colorAccentPredicate = { index, _ ->
                when (tags[index].purity) {
                    Tag.TagPurity.SFW -> SuggestionChipColorsAccent.Standard
                    Tag.TagPurity.SKETCHY -> SuggestionChipColorsAccent.Warning
                    Tag.TagPurity.NSFW -> SuggestionChipColorsAccent.Dangerous
                }
            },
        )
    }

    if (colors.isNotEmpty()) {
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
            onItemClick = { index, _ -> onColorChipClick.invoke(colors[index]) },
            colorAccentPredicate = { index, _ ->
                SuggestionChipColorsAccent.Custom(
                    containerColor = Color(colors[index].name.toColorInt()),
                    contentColor = Color(colors[index].name.toColorInt()),
                )
            },
        )
    }
}

@Composable
private fun RowScope.SavePictureButton(
    saveButtonState: PictureInformationUiState.PictureSavingUiState,
    onSavePictureClick: () -> Unit
) {
    var downloading: Boolean by rememberSaveable { mutableStateOf(false) }
    downloading = when (saveButtonState) {
        PictureInformationUiState.PictureSavingUiState.Prepared -> false
        is PictureInformationUiState.PictureSavingUiState.Error -> false
        PictureInformationUiState.PictureSavingUiState.Loading -> true
        PictureInformationUiState.PictureSavingUiState.Saving -> true
        PictureInformationUiState.PictureSavingUiState.Success -> false
    }
    PixelsSurfaceButton(
        text = "Save",
        onClick = { onSavePictureClick.invoke() },
        enabled = !downloading,
        iconContent = {
            AnimatedVisibility(
                visible = downloading,
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