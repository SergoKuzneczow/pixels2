package com.sergokuzneczow.bottom_sheet_page_filter.impl.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.bottom_sheet_page_filter.R
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.PageFilterItem
import com.sergokuzneczow.core.system_components.choice_segments.MultiChoice
import com.sergokuzneczow.core.system_components.choice_segments.PixelsMultiChoiceSegmentedButtonRow
import com.sergokuzneczow.core.system_components.choice_segments.MultiChoiceStrategy
import com.sergokuzneczow.core.system_components.choice_segments.PixelsOutlinedChoiceSegmentedButtonRow
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.models.PageFilter

@Composable
internal fun CategoriesChoice(
    options: List<PageFilterItem<PageFilter.PictureCategories>>,
    onSelect: (options: List<PageFilterItem<PageFilter.PictureCategories>>) -> Unit,
) {
    Text(
        text = stringResource(R.string.categories_chips),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding)
    )
    PixelsOutlinedChoiceSegmentedButtonRow(
        values = options,
        isSelected = { _, itemValue -> itemValue.isSelected },
        isTitle = { _, itemValue -> itemValue.title },
        onSelect = { selectedIndex, _ ->
            onSelect.invoke(options.mapIndexed { index, item ->
                if (selectedIndex == index) {
                    if (item.isSelected) {
                        val counter = options.fold(0) { acc, item -> if (item.isSelected) acc + 1 else acc }
                        if (counter > 1) item.copy(isSelected = false) else item
                    } else item.copy(isSelected = true)
                } else item
            })
        },
        modifier = Modifier.padding(top = Dimensions.Padding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
        isIcon = { _, itemValue -> if (itemValue.isSelected) PixelsIcons.selector else null },
    )
}