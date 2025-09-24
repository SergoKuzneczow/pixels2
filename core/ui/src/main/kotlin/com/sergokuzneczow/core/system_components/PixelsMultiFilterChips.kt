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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun PixelsMultiFilterChip(
    chips: List<PixelsMultiFilterChipItem>,
    selectedValues: (values: List<Boolean>) -> Unit,
    modifier: Modifier = Modifier,
    multiFilterStrategy: PixelsMultiFilterStrategy = PixelsMultiFilterStrategy.NOT_EMPTY,
    colorAccentPredicate: (index: Int, value: PixelsMultiFilterChipItem) -> ColorsAccent = { _, _ -> ColorsAccent.STANDARD }
) {
    val choiceState: MutableState<List<Boolean>> = remember { mutableStateOf(chips.map { it.startState }) }

    Row(modifier = modifier.fillMaxWidth()) {
        chips.forEachIndexed { index, item ->
            FilterChip(
                onClick = {
                    if (!choiceState.value[index]) {
                        val newItemsState: List<Boolean> = choiceState.value.mapIndexed { i, s -> if (index == i) true else s }
                        choiceState.value = newItemsState
                    } else {
                        when (multiFilterStrategy) {
                            PixelsMultiFilterStrategy.MAYBE_EMPTY -> {
                                val newItemsState: List<Boolean> = choiceState.value.mapIndexed { i, s -> if (index == i) false else s }
                                choiceState.value = newItemsState
                            }

                            PixelsMultiFilterStrategy.NOT_EMPTY -> {
                                val selectedItemsMoreSingle: Boolean = choiceState.value.filter { it }.size > 1
                                if (selectedItemsMoreSingle) {
                                    val newItemsState: List<Boolean> = choiceState.value.mapIndexed { i, s -> if (index == i) false else s }
                                    choiceState.value = newItemsState
                                }
                            }
                        }
                    }
                    selectedValues.invoke(choiceState.value)
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
                selected = choiceState.value[index],
                border = BorderStroke(0.dp, Color(0, 0, 0, 0)),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun colorsAccent(colorsAccent: ColorsAccent): SelectableChipColors {
    return when (colorsAccent) {
        ColorsAccent.STANDARD -> {
            FilterChipDefaults.filterChipColors(
                containerColor = MaterialTheme.colorScheme.surface,
                iconColor = MaterialTheme.colorScheme.onSurface,
                labelColor = MaterialTheme.colorScheme.onSurface,
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                selectedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            )
        }

        ColorsAccent.WARNING -> {
            FilterChipDefaults.filterChipColors(
                containerColor = WarningColors.containerColor,
                iconColor = WarningColors.contentColor,
                labelColor = WarningColors.contentColor,
                selectedContainerColor = WarningColors.selectedContainerColor,
                selectedLabelColor = WarningColors.selectedContentColor,
                selectedLeadingIconColor = WarningColors.selectedContentColor,
            )
        }

        ColorsAccent.DANGEROUS -> {
            FilterChipDefaults.filterChipColors(
                containerColor = DangerousColors.containerColor,
                iconColor = DangerousColors.contentColor,
                labelColor = DangerousColors.contentColor,
                selectedContainerColor = DangerousColors.selectedContainerColor,
                selectedLabelColor = DangerousColors.selectedContentColor,
                selectedLeadingIconColor = DangerousColors.selectedContentColor,
            )
        }
    }
}

public data class PixelsMultiFilterChipItem(
    val title: String,
    val startState: Boolean,
)

public enum class PixelsMultiFilterStrategy {
    MAYBE_EMPTY,
    NOT_EMPTY,
}

public enum class ColorsAccent {
    STANDARD,
    WARNING,
    DANGEROUS,
}

private object WarningColors {
    val containerColor = Color(1f, 0.6f, 0.0f, 0.2f)
    val selectedContainerColor = Color(1f, 0.7f, 0.0f, 1f)
    val contentColor = Color(0.0f, 0.0f, 0.0f, 1f)
    val selectedContentColor = Color(1.0f, 1.0f, 1.0f, 1f)
}

private object DangerousColors {
    val containerColor = Color(1f, 0.0f, 0.0f, 0.2f)
    val selectedContainerColor = Color(1f, 0.0f, 0.0f, 1f)
    val contentColor = Color(0.0f, 0.0f, 0.0f, 1f)
    val selectedContentColor = Color(1.0f, 1.0f, 1.0f, 1f)
}

@ThemePreviews
@Composable
internal fun PixelsMultiFilterChipPreview() {
    PixelsTheme {
        PixelsMultiFilterChip(
            chips = listOf(
                PixelsMultiFilterChipItem(
                    title = "Preview",
                    startState = true,
                ),
                PixelsMultiFilterChipItem(
                    title = "Preview",
                    startState = false,
                ),
                PixelsMultiFilterChipItem(
                    title = "Preview",
                    startState = false,
                ),
            ),
            selectedValues = {},
            colorAccentPredicate = { index, value ->
                when (index) {
                    1 -> ColorsAccent.WARNING
                    2 -> ColorsAccent.DANGEROUS
                    else -> ColorsAccent.STANDARD
                }
            }
        )
    }
}