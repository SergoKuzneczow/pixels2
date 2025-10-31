package com.sergokuzneczow.core.system_components.chip_segments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ChipColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults.suggestionChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun PixelsSuggestionFlowRow(
    suggestionChips: List<SuggestionChip>,
    modifier: Modifier = Modifier,
    onItemClick: (index: Int, suggestionChip: SuggestionChip) -> Unit = { _, _ -> },
    colorAccentPredicate: (index: Int, suggestionChip: SuggestionChip) -> SuggestionChipColorsAccent = { _, _ -> SuggestionChipColorsAccent.Standard }
) {
    FlowRow(
        maxItemsInEachRow = 8,
        modifier = modifier.fillMaxWidth(),
    ) {
        suggestionChips.forEachIndexed { index, chip ->
            SuggestionChip(
                onClick = { onItemClick.invoke(index, chip) },
                label = {
                    Box {
                        Text(
                            text = chip.label,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )
                    }
                },
                modifier = Modifier.padding(horizontal = 4.dp),
                shape = Dimensions.PixelsShape,
                colors = colorsAccent(colorAccentPredicate.invoke(index, chip)),
                border = Dimensions.Border,
            )
        }
    }
}

public data class SuggestionChip(
    val label: String,
    val startState: Boolean = false,
)

@Composable
private fun colorsAccent(colorsAccent: SuggestionChipColorsAccent): ChipColors {
    return when (colorsAccent) {
        SuggestionChipColorsAccent.Standard -> {
            suggestionChipColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                labelColor = MaterialTheme.colorScheme.onSurface,
                iconContentColor = MaterialTheme.colorScheme.onSurface,
            )
        }

        SuggestionChipColorsAccent.Warning -> {
            suggestionChipColors(
                containerColor = SuggestionChipWarningColors.containerColor,
                labelColor = MaterialTheme.colorScheme.onSurface,
                iconContentColor = SuggestionChipWarningColors.contentColor,
            )
        }

        SuggestionChipColorsAccent.Dangerous -> {
            suggestionChipColors(
                containerColor = SuggestionChipDangerousColors.containerColor,
                labelColor = MaterialTheme.colorScheme.onSurface,
                iconContentColor = SuggestionChipDangerousColors.contentColor,
            )
        }

        is SuggestionChipColorsAccent.Custom -> {
            suggestionChipColors(
                containerColor = colorsAccent.containerColor,
                labelColor = colorsAccent.contentColor,
                iconContentColor = colorsAccent.contentColor,
            )
        }
    }
}

public sealed interface SuggestionChipColorsAccent {
    public data object Standard : SuggestionChipColorsAccent
    public data object Warning : SuggestionChipColorsAccent
    public data object Dangerous : SuggestionChipColorsAccent
    public data class Custom(
        val containerColor: Color,
        val contentColor: Color,
    ) : SuggestionChipColorsAccent
}

private object SuggestionChipWarningColors {
    val containerColor: Color = Color(1f, 1f, 0.0f, 0.5f)
    val contentColor: Color = Color(0.0f, 0.0f, 0.0f, 1f)
}

private object SuggestionChipDangerousColors {
    val containerColor: Color = Color(1f, 0.0f, 0.0f, 0.5f)
    val contentColor: Color = Color(0.0f, 0.0f, 0.0f, 1f)
}

@ThemePreviews
@Composable
private fun FlowSuggestionChipsPreview() {
    PixelsTheme {
        PixelsSuggestionFlowRow(
            suggestionChips = listOf(
                SuggestionChip(label = "Preview"),
                SuggestionChip(label = "Short"),
                SuggestionChip(label = "LongPreview"),
                SuggestionChip(label = "Preview"),
                SuggestionChip(label = "AnotherLongPreview"),
                SuggestionChip(label = "Preview"),
                SuggestionChip(label = "VeryLongPreview"),
                SuggestionChip(label = "Preview"),
                SuggestionChip(label = "AnotherVeryLongPreview"),
                SuggestionChip(label = "Short"),
            ),
            onItemClick = { _, _ -> },
            colorAccentPredicate = { index, suggestionChip ->
                when (index) {
                    2 -> SuggestionChipColorsAccent.Warning
                    3 -> SuggestionChipColorsAccent.Dangerous
                    else -> SuggestionChipColorsAccent.Standard
                }
            }
        )
    }
}