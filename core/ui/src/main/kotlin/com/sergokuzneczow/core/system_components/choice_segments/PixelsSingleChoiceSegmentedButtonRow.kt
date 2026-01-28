package com.sergokuzneczow.core.system_components.choice_segments

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun <T> PixelsOutlinedChoiceSegmentedButtonRow(
    values: List<T>,
    isSelected: (itemIndex: Int, itemValue: T) -> Boolean,
    isTitle: (itemIndex: Int, itemValue: T) -> String,
    onSelect: (selectedIndex: Int, selectedValue: T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isIcon: ((itemIndex: Int, itemValue: T) -> ImageVector?)? = null,
    isColor: @Composable ((color: SegmentedButtonColors, itemIndex: Int, itemValue: T) -> SegmentedButtonColors?)? = null,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier.fillMaxWidth(),
        content = {
            values.forEachIndexed { index, value ->
                SegmentedButton(
                    selected = isSelected.invoke(index, value),
                    onClick = { onSelect.invoke(index, value) },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = values.size
                    ),
                    enabled = enabled,
                    colors = isColor?.invoke(segmentButtonColors(), index, value) ?: segmentButtonColors(),
                    border = Dimensions.Border,
                    icon = {
                        isIcon?.invoke(index, value)?.let { vectorIcon ->
                            Icon(
                                imageVector = vectorIcon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    },
                    label = {
                        Text(
                            text = isTitle.invoke(index, value),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isSelected.invoke(index, value)) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
            }
        }
    )
}

@Composable
public fun <T> PixelsSingleChoiceSegmentedButtonRow(
    options: List<SingleChoice<T>>,
    onItemSelect: (index: Int, value: T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    startSelector: Int = 0,
    hasIcon: Boolean = false,
) {
    var selectedIndex: Int by remember { mutableIntStateOf(startSelector) }

    SingleChoiceSegmentedButtonRow(
        modifier = modifier.fillMaxWidth(),
        content = {
            options.forEachIndexed { index, choice ->
                SegmentedButton(
                    selected = index == selectedIndex,
                    onClick = {
                        selectedIndex = index
                        onItemSelect.invoke(index, choice.value)
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    enabled = enabled,
                    colors = segmentButtonColors(),
                    border = Dimensions.Border,
                    icon = {
                        if (index == selectedIndex && hasIcon) {
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
                            color = if (index == selectedIndex) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
            }
        }
    )
}

@Composable
private fun segmentButtonColors(): SegmentedButtonColors = SegmentedButtonDefaults.colors().copy(
    activeContainerColor = MaterialTheme.colorScheme.primary,
    activeContentColor = MaterialTheme.colorScheme.onPrimary,
    activeBorderColor = MaterialTheme.colorScheme.primary,
    inactiveContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    inactiveContentColor = MaterialTheme.colorScheme.onSurface,
    inactiveBorderColor = MaterialTheme.colorScheme.surfaceContainer,
)

public data class SingleChoice<T>(
    val label: String,
    val value: T,
)

@ThemePreviews
@Composable
private fun SingleChoiceSegmentedButtonPreview() {
    PixelsTheme {
        PixelsSingleChoiceSegmentedButtonRow(
            options = listOf(
                SingleChoice("Preview", "value"),
                SingleChoice("Preview", "value"),
                SingleChoice("Preview", "value"),
            ),
            onItemSelect = { _, _ -> },
        )
    }
}