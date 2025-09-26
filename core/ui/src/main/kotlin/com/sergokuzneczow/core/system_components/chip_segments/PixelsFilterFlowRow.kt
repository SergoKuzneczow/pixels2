package com.sergokuzneczow.core.system_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun FlowFilterChips(
    chips: List<FilterChip>,
    modifier: Modifier = Modifier,
    onSelectedChanged: (chipsState: List<Boolean>) -> Unit = {},
    filterChipsStrategy: FilterChipsStrategy = FilterChipsStrategy.MAYBE_EMPTY,
    colorAccentPredicate: (index: Int, chip: FilterChip) -> FilterChipColorsAccent = { _, _ -> FilterChipColorsAccent.STANDARD }
) {
    val choiceState: MutableState<List<Boolean>> = remember { mutableStateOf(chips.map { it.startState }) }

    FlowRow(
        maxItemsInEachRow = 8,
        modifier = modifier.fillMaxWidth(),
    ) {
        chips.forEachIndexed { index, chip ->
            FilterChip(
                onClick = {
                    if (!choiceState.value[index]) choiceState.value = choiceState.value.selectElement(index, filterChipsStrategy)
                    else choiceState.value = choiceState.value.deselectElement(index, filterChipsStrategy)
                    onSelectedChanged.invoke(choiceState.value)
                },
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
                shape = RoundedCornerShape(16.dp),
                colors = colorsAccent(colorAccentPredicate.invoke(index, chip)),
                selected = choiceState.value[index],
                border = Dimensions.Border,
            )
        }
    }
}

private fun List<Boolean>.selectElement(
    index: Int,
    filterChipsStrategy: FilterChipsStrategy,
): List<Boolean> {
    return when (filterChipsStrategy) {
        FilterChipsStrategy.MAYBE_EMPTY -> {
            val newItemsState: List<Boolean> = this.mapIndexed { i, s -> if (index == i) true else s }
            newItemsState
        }

        FilterChipsStrategy.SINGLE -> {
            val newItemsState: List<Boolean> = this.mapIndexed { i, s -> index == i }
            newItemsState
        }

        FilterChipsStrategy.NOT_EMPTY -> {
            val newItemsState: List<Boolean> = this.mapIndexed { i, s -> if (index == i) true else s }
            newItemsState
        }
    }
}

private fun List<Boolean>.deselectElement(
    index: Int,
    filterChipsStrategy: FilterChipsStrategy,
): List<Boolean> {
    return when (filterChipsStrategy) {
        FilterChipsStrategy.MAYBE_EMPTY -> {
            val newItemsState: List<Boolean> = this.mapIndexed { i, s -> if (index == i) false else s }
            newItemsState
        }

        FilterChipsStrategy.SINGLE -> {
            this
        }

        FilterChipsStrategy.NOT_EMPTY -> {
            val selectedItemsMoreSingle: Boolean = this.filter { it }.size > 1
            if (selectedItemsMoreSingle) {
                val newItemsState: List<Boolean> = this.mapIndexed { i, s -> if (index == i) false else s }
                newItemsState
            } else this
        }
    }
}

public data class FilterChip(
    val label: String,
    val startState: Boolean = false,
)

public enum class FilterChipsStrategy {
    MAYBE_EMPTY,
    SINGLE,
    NOT_EMPTY,
}

@Composable
private fun colorsAccent(colorsAccent: FilterChipColorsAccent): SelectableChipColors {
    return when (colorsAccent) {
        FilterChipColorsAccent.STANDARD -> {
            FilterChipDefaults.filterChipColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                iconColor = MaterialTheme.colorScheme.onSurface,
                labelColor = MaterialTheme.colorScheme.onSurface,
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                selectedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            )
        }

        FilterChipColorsAccent.WARNING -> {
            FilterChipDefaults.filterChipColors(
                containerColor = FilterChipWarningColors.containerColor,
                iconColor = FilterChipWarningColors.contentColor,
                labelColor = FilterChipWarningColors.contentColor,
                selectedContainerColor = FilterChipWarningColors.selectedContainerColor,
                selectedLabelColor = FilterChipWarningColors.selectedContentColor,
                selectedLeadingIconColor = FilterChipWarningColors.selectedContentColor,
            )
        }

        FilterChipColorsAccent.DANGEROUS -> {
            FilterChipDefaults.filterChipColors(
                containerColor = FilterChipDangerousColors.containerColor,
                iconColor = FilterChipDangerousColors.contentColor,
                labelColor = FilterChipDangerousColors.contentColor,
                selectedContainerColor = FilterChipDangerousColors.selectedContainerColor,
                selectedLabelColor = FilterChipDangerousColors.selectedContentColor,
                selectedLeadingIconColor = FilterChipDangerousColors.selectedContentColor,
            )
        }
    }
}

public enum class FilterChipColorsAccent {
    STANDARD,
    WARNING,
    DANGEROUS,
}

private object FilterChipWarningColors {
    val containerColor: Color = Color(1f, 1f, 0.0f, 0.5f)
    val selectedContainerColor = Color(1f, 1f, 0.0f, 0.9f)
    val contentColor: Color = Color(0.0f, 0.0f, 0.0f, 1f)
    val selectedContentColor: Color = Color(1.0f, 1.0f, 1.0f, 1f)
}

private object FilterChipDangerousColors {
    val containerColor: Color = Color(1f, 0.0f, 0.0f, 0.5f)
    val selectedContainerColor: Color = Color(1f, 0.0f, 0.0f, 0.9f)
    val contentColor: Color = Color(0.0f, 0.0f, 0.0f, 1f)
    val selectedContentColor: Color = Color(1.0f, 1.0f, 1.0f, 1f)
}

@ThemePreviews
@Composable
private fun FlowFilterChipsPreview() {
    PixelsTheme {
        FlowFilterChips(
            chips = listOf(
                FilterChip(label = "Preview", startState = true),
                FilterChip(label = "Short"),
                FilterChip(label = "LongPreview", startState = true),
                FilterChip(label = "Preview"),
                FilterChip(label = "AnotherLongPreview"),
                FilterChip(label = "Preview"),
                FilterChip(label = "VeryLongPreview"),
                FilterChip(label = "Preview"),
                FilterChip(label = "AnotherVeryLongPreview"),
                FilterChip(label = "Short"),
            ),
            onSelectedChanged = {},
        )
    }
}