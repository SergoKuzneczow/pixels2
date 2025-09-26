package com.sergokuzneczow.bottom_sheet_page_filter.impl.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.bottom_sheet_page_filter.R
import com.sergokuzneczow.core.system_components.choice_segments.SingleChoice
import com.sergokuzneczow.core.system_components.choice_segments.PixelsSingleChoiceSegmentedButtonRow
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.PageFilter

@Composable
internal fun SortingChoice(
    startValue: PageFilter.PictureSorting,
    selectedValue: (sorting: PageFilter.PictureSorting) -> Unit,
) {
    val options: List<SingleChoice<PageFilter.PictureSorting>> = listOf(
        SingleChoice(
            label = "Views",
            value = PageFilter.PictureSorting.VIEWS,
        ),
        SingleChoice(
            label = "Loved",
            value = PageFilter.PictureSorting.FAVORITES,
        ),
        SingleChoice(
            label = "Bests",
            value = PageFilter.PictureSorting.TOP_LIST,
        ),
        SingleChoice(
            label = "Date",
            value = PageFilter.PictureSorting.DATE_ADDED,
        ),
    )
    val startSelector = options.indexOfFirst { it.value == startValue }
    Text(
        text = stringResource(R.string.sorting_chips),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding)
    )
    PixelsSingleChoiceSegmentedButtonRow(
        options = options,
        onItemSelect = { index, value -> selectedValue.invoke(value) },
        modifier = Modifier.padding(top = Dimensions.Padding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
        startSelector = if (startSelector == -1) 0 else startSelector
    )
}