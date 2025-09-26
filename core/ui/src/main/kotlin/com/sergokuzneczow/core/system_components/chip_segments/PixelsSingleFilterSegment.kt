package com.sergokuzneczow.core.system_components.chip_segments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import kotlin.collections.forEachIndexed
import kotlin.collections.indexOfFirst

@Composable
@Deprecated(message = "Now you need use implement PixelsFilterSegment.kt with filterSegmentStrategy=FilterSegmentStrategy.Single.")
public fun <T> PixelsSingleFilterSegment(
    chips: List<SingleFilterChip<T>>,
    startValue: T,
    selectedValue: (value: T) -> Unit,
    modifier: Modifier = Modifier,
) {
    val startSelectedIndex = chips.indexOfFirst { it.value == startValue }
    if (startSelectedIndex == -1) throw IllegalArgumentException("Can't to find equals element in chips for startValue.")
    var selectedIndex by remember { mutableIntStateOf(startSelectedIndex) }

    Row(modifier = modifier.fillMaxWidth()) {
        chips.forEachIndexed { index, item ->
            FilterChip(
                onClick = {
                    selectedIndex = index
                    selectedValue.invoke(item.value)
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    iconColor = MaterialTheme.colorScheme.onSurface,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                ),
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
                selected = selectedIndex == index,
                border = BorderStroke(0.dp, Color(0, 0, 0, 0)),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
        }
    }
}

public data class SingleFilterChip<T>(
    val title: String,
    val value: T,
)

@ThemePreviews
@Composable
private fun PixelsSingleFilterChipPreview() {
    PixelsTheme {
        PixelsSingleFilterSegment(
            chips = listOf(
                SingleFilterChip("Preview", 1),
                SingleFilterChip("Preview", 2),
                SingleFilterChip("Preview", 3),
            ),
            startValue = 1,
            selectedValue = {}
        )
    }
}