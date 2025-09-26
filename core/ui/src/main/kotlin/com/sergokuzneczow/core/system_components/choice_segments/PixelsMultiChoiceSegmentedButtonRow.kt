package com.sergokuzneczow.core.system_components.choice_segments

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews


@Composable
public fun PixelsMultiChoiceSegmentedButtonRow(
    options: List<MultiChoice>,
    onCheckedChange: (itemsState: List<Boolean>) -> Unit,
    modifier: Modifier = Modifier,
    multiChoiceStrategy: MultiChoiceStrategy = MultiChoiceStrategy.NOT_EMPTY,
    colorAccentPredicate: (index: Int, value: MultiChoice) -> MultiChoiceColorsAccent = { _, _ -> MultiChoiceColorsAccent.STANDARD }
) {
    var selectedIndexes: List<Boolean> by remember { mutableStateOf(options.map { it.selected }) }

    MultiChoiceSegmentedButtonRow(
        modifier = modifier.fillMaxWidth(),
        content = {
            options.forEachIndexed { index, choice ->
                SegmentedButton(
                    checked = selectedIndexes[index],
                    onCheckedChange = {
                        if (!selectedIndexes[index]) {
                            val new: List<Boolean> = selectedIndexes.mapIndexed { i, s -> if (index == i) true else s }
                            selectedIndexes = new
                        } else {
                            when (multiChoiceStrategy) {
                                MultiChoiceStrategy.MAYBE_EMPTY -> {
                                    val new: List<Boolean> = selectedIndexes.mapIndexed { i, s -> if (index == i) false else s }
                                    selectedIndexes = new
                                }

                                MultiChoiceStrategy.NOT_EMPTY -> {
                                    val selectedItemsMoreSingle: Boolean = selectedIndexes.filter { it }.size > 1
                                    if (selectedItemsMoreSingle) {
                                        val new: List<Boolean> = selectedIndexes.mapIndexed { i, s -> if (index == i) false else s }
                                        selectedIndexes = new
                                    }
                                }
                            }
                        }
                        onCheckedChange.invoke(selectedIndexes)
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    colors = colorsAccent(colorsAccent = colorAccentPredicate.invoke(index, choice)),
                    border = Dimensions.Border,
                    icon = {
                        if (selectedIndexes[index]) {
                            Icon(
                                imageVector = PixelsIcons.selector,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    },
                    label = {
                        Text(
                            text = choice.label,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                )
            }
        }
    )
}

@Composable
private fun colorsAccent(colorsAccent: MultiChoiceColorsAccent): SegmentedButtonColors {
    val standard: SegmentedButtonColors = SegmentedButtonDefaults.colors().copy(
        activeContainerColor = MaterialTheme.colorScheme.primary,
        activeContentColor = MaterialTheme.colorScheme.onPrimary,
        activeBorderColor = MaterialTheme.colorScheme.primary,
        inactiveContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        inactiveContentColor = MaterialTheme.colorScheme.onSurface,
        inactiveBorderColor = MaterialTheme.colorScheme.surfaceContainer,
    )
    return when (colorsAccent) {
        MultiChoiceColorsAccent.STANDARD -> {
            standard
        }

        MultiChoiceColorsAccent.WARNING -> {
            standard.copy(
                activeContainerColor = MultiChoiceWarningColors.selectedContainerColor,
                activeContentColor = MultiChoiceWarningColors.selectedContentColor,
                activeBorderColor = MultiChoiceWarningColors.selectedContainerColor,
                inactiveContainerColor = MultiChoiceWarningColors.containerColor,
                inactiveContentColor = MultiChoiceWarningColors.contentColor,
                inactiveBorderColor = MultiChoiceWarningColors.containerColor,
            )
        }

        MultiChoiceColorsAccent.DANGEROUS -> {
            standard.copy(
                activeContainerColor = MultiChoiceDangerousColors.selectedContainerColor,
                activeContentColor = MultiChoiceDangerousColors.selectedContentColor,
                activeBorderColor = MultiChoiceDangerousColors.selectedContainerColor,
                inactiveContainerColor = MultiChoiceDangerousColors.containerColor,
                inactiveContentColor = MultiChoiceDangerousColors.contentColor,
                inactiveBorderColor = MultiChoiceDangerousColors.containerColor,
            )
        }
    }
}

public data class MultiChoice(
    val label: String,
    val selected: Boolean = false,
)

public enum class MultiChoiceStrategy {
    MAYBE_EMPTY,
    NOT_EMPTY,
}

public enum class MultiChoiceColorsAccent {
    STANDARD,
    WARNING,
    DANGEROUS,
}

private object MultiChoiceWarningColors {
    val containerColor: Color = Color(1f, 0.6f, 0.0f, 0.2f)
    val selectedContainerColor: Color = Color(1f, 0.7f, 0.0f, 1f)
    val contentColor: Color = Color(0.0f, 0.0f, 0.0f, 1f)
    val selectedContentColor: Color = Color(1.0f, 1.0f, 1.0f, 1f)
}

private object MultiChoiceDangerousColors {
    val containerColor: Color = Color(1f, 0.0f, 0.0f, 0.2f)
    val selectedContainerColor: Color = Color(1f, 0.0f, 0.0f, 1f)
    val contentColor: Color = Color(0.0f, 0.0f, 0.0f, 1f)
    val selectedContentColor: Color = Color(1.0f, 1.0f, 1.0f, 1f)
}

@ThemePreviews
@Composable
private fun SingleChoiceSegmentedButtonPreview() {
    PixelsTheme {
        PixelsMultiChoiceSegmentedButtonRow(
            options = listOf(
                MultiChoice(label = "Preview"),
                MultiChoice(label = "Preview"),
                MultiChoice(label = "Preview"),
                MultiChoice(label = "Preview"),
            ),
            onCheckedChange = {},
            modifier = Modifier.fillMaxWidth(),
            multiChoiceStrategy = MultiChoiceStrategy.NOT_EMPTY,
            colorAccentPredicate = { index, value ->
                when (index) {
                    2 -> MultiChoiceColorsAccent.WARNING
                    3 -> MultiChoiceColorsAccent.DANGEROUS
                    else -> MultiChoiceColorsAccent.STANDARD
                }
            }
        )
    }
}