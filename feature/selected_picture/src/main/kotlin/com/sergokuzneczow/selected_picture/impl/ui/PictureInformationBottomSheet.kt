package com.sergokuzneczow.selected_picture.impl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.core.graphics.toColorInt
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
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    tags: List<Tag>,
    colors: List<com.sergokuzneczow.models.Color>,
    onTagChipClick: (tag: Tag) -> Unit,
    onColorChipClick: (color: com.sergokuzneczow.models.Color) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { coroutineScope.launch { bottomSheetState.hide() } },
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        content = {
            PictureInformation(
                tags = tags,
                colors = colors,
                onTagChipClick = onTagChipClick,
                onColorChipClick = onColorChipClick,
            )
        }
    )
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
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

        Spacer(modifier = Modifier.height(Dimensions.LargePadding))
    }
}