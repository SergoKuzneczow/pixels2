package com.sergokuzneczow.core.system_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun PixelsFilterSegment(
    chips: List<FilterSegmentChip>,
    selectedValues: (values: List<Boolean>) -> Unit,
    modifier: Modifier = Modifier,
    filterSegmentStrategy: FilterSegmentStrategy = FilterSegmentStrategy.NOT_EMPTY,
    colorAccentPredicate: (index: Int, value: FilterSegmentChip) -> FilterSegmentColorsAccent = { _, _ -> FilterSegmentColorsAccent.STANDARD }
) {
    var chipState: List<Boolean> by remember { mutableStateOf(chips.map { it.startState }) }

    Row(modifier = modifier.fillMaxWidth()) {
        chips.forEachIndexed { index, item ->
            FilterChip(
                onClick = {
//                    if (!choiceState.value[index]) {
//                        val newItemsState: List<Boolean> = choiceState.value.mapIndexed { i, s -> if (index == i) true else s }
//                        choiceState.value = newItemsState
//                    } else {
//                        when (multiFilterStrategy) {
//                            MultiFilterStrategy.MAYBE_EMPTY -> {
//                                val newItemsState: List<Boolean> = choiceState.value.mapIndexed { i, s -> if (index == i) false else s }
//                                choiceState.value = newItemsState
//                            }
//
//                            MultiFilterStrategy.NOT_EMPTY -> {
//                                val selectedItemsMoreSingle: Boolean = choiceState.value.filter { it }.size > 1
//                                if (selectedItemsMoreSingle) {
//                                    val newItemsState: List<Boolean> = choiceState.value.mapIndexed { i, s -> if (index == i) false else s }
//                                    choiceState.value = newItemsState
//                                }
//                            }
//                        }
//                    }
                    chipState = if (!chipState[index]) chipState.selectElement(index, filterSegmentStrategy)
                    else chipState.deselectElement(index, filterSegmentStrategy)
                    selectedValues.invoke(chipState)
                },
                colors = colorsAccent(colorAccentPredicate.invoke(index, item)),
                label = {
                    Box {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                selected = chipState[index],
                border = BorderStroke(0.dp, Color(0, 0, 0, 0)),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
        }
    }
}

private fun List<Boolean>.selectElement(
    index: Int,
    filterSegmentStrategy: FilterSegmentStrategy,
): List<Boolean> {
    return when (filterSegmentStrategy) {
        FilterSegmentStrategy.MAYBE_EMPTY -> this.mapIndexed { i, s -> if (index == i) true else s }
        FilterSegmentStrategy.SINGLE -> this.mapIndexed { i, s -> index == i }
        FilterSegmentStrategy.NOT_EMPTY -> this.mapIndexed { i, s -> if (index == i) true else s }
    }
}

private fun List<Boolean>.deselectElement(
    index: Int,
    filterSegmentStrategy: FilterSegmentStrategy,
): List<Boolean> {
    return when (filterSegmentStrategy) {
        FilterSegmentStrategy.MAYBE_EMPTY -> this.mapIndexed { i, s -> if (index == i) false else s }
        FilterSegmentStrategy.SINGLE -> this
        FilterSegmentStrategy.NOT_EMPTY -> if (this.filter { selected -> selected }.size > 1) this.mapIndexed { i, current -> if (index == i) false else current } else this
    }
}

@Composable
private fun colorsAccent(filterSegmentColorsAccent: FilterSegmentColorsAccent): SelectableChipColors {
    return when (filterSegmentColorsAccent) {
        FilterSegmentColorsAccent.STANDARD -> {
            FilterChipDefaults.filterChipColors(
                containerColor = MaterialTheme.colorScheme.surface,
                iconColor = MaterialTheme.colorScheme.onSurface,
                labelColor = MaterialTheme.colorScheme.onSurface,
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                selectedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            )
        }

        FilterSegmentColorsAccent.WARNING -> {
            FilterChipDefaults.filterChipColors(
                containerColor = FilterSegmentWarningColors.containerColor,
                iconColor = FilterSegmentWarningColors.contentColor,
                labelColor = FilterSegmentWarningColors.contentColor,
                selectedContainerColor = FilterSegmentWarningColors.selectedContainerColor,
                selectedLabelColor = FilterSegmentWarningColors.selectedContentColor,
                selectedLeadingIconColor = FilterSegmentWarningColors.selectedContentColor,
            )
        }

        FilterSegmentColorsAccent.DANGEROUS -> {
            FilterChipDefaults.filterChipColors(
                containerColor = FilterSegmentDangerousColors.containerColor,
                iconColor = FilterSegmentDangerousColors.contentColor,
                labelColor = FilterSegmentDangerousColors.contentColor,
                selectedContainerColor = FilterSegmentDangerousColors.selectedContainerColor,
                selectedLabelColor = FilterSegmentDangerousColors.selectedContentColor,
                selectedLeadingIconColor = FilterSegmentDangerousColors.selectedContentColor,
            )
        }
    }
}

public data class FilterSegmentChip(
    val title: String,
    val startState: Boolean,
)

public enum class FilterSegmentStrategy {
    MAYBE_EMPTY,
    SINGLE,
    NOT_EMPTY,
}

public enum class FilterSegmentColorsAccent {
    STANDARD,
    WARNING,
    DANGEROUS,
}

private object FilterSegmentWarningColors {
    val containerColor = Color(1f, 0.6f, 0.0f, 0.2f)
    val selectedContainerColor = Color(1f, 0.7f, 0.0f, 1f)
    val contentColor = Color(0.0f, 0.0f, 0.0f, 1f)
    val selectedContentColor = Color(1.0f, 1.0f, 1.0f, 1f)
}

private object FilterSegmentDangerousColors {
    val containerColor = Color(1f, 0.0f, 0.0f, 0.2f)
    val selectedContainerColor = Color(1f, 0.0f, 0.0f, 1f)
    val contentColor = Color(0.0f, 0.0f, 0.0f, 1f)
    val selectedContentColor = Color(1.0f, 1.0f, 1.0f, 1f)
}

@ThemePreviews
@Composable
internal fun PixelsMultiFilterChipPreview() {
    PixelsTheme {
        PixelsFilterSegment(
            chips = listOf(
                FilterSegmentChip(
                    title = "Preview",
                    startState = true,
                ),
                FilterSegmentChip(
                    title = "Preview",
                    startState = false,
                ),
                FilterSegmentChip(
                    title = "Preview",
                    startState = false,
                ),
            ),
            selectedValues = {},
            colorAccentPredicate = { index, value ->
                when (index) {
                    1 -> FilterSegmentColorsAccent.WARNING
                    2 -> FilterSegmentColorsAccent.DANGEROUS
                    else -> FilterSegmentColorsAccent.STANDARD
                }
            }
        )
    }
}