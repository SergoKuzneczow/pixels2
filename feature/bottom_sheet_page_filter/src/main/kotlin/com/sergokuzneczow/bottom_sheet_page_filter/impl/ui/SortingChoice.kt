package com.sergokuzneczow.bottom_sheet_page_filter.impl.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.bottom_sheet_page_filter.R
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.PageFilterItem
import com.sergokuzneczow.core.system_components.choice_segments.PixelsOutlinedChoiceSegmentedButtonRow
import com.sergokuzneczow.core.system_components.choice_segments.PixelsSingleChoiceSegmentedButtonRow
import com.sergokuzneczow.core.system_components.choice_segments.SingleChoice
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.models.PageFilter

@Composable
internal fun SortingChoice(
    options: List<PageFilterItem<PageFilter.PictureSorting>>,
    onSelect: (options: List<PageFilterItem<PageFilter.PictureSorting>>) -> Unit,
) {
    Text(
        text = stringResource(R.string.sorting_chips),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding)
    )
    PixelsOutlinedChoiceSegmentedButtonRow(
        values = options,
        isSelected = { _, selectedValue -> selectedValue.isSelected },
        isTitle = { _, selectedValue -> selectedValue.title },
        onSelect = { selectedIndex, _ ->
            onSelect.invoke(options.mapIndexed { index, item ->
                if (selectedIndex == index) item.copy(isSelected = !item.isSelected)
                else item.copy(isSelected = false)
            })
        },
        modifier = Modifier.padding(top = Dimensions.Padding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
        isIcon = { _, selectedValue -> if (selectedValue.isSelected) PixelsIcons.selector else null }
    )
}