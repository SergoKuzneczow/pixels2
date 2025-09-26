package com.sergokuzneczow.bottom_sheet_page_filter.impl.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.bottom_sheet_page_filter.R
import com.sergokuzneczow.core.system_components.choice_segments.MultiChoice
import com.sergokuzneczow.core.system_components.choice_segments.PixelsMultiChoiceSegmentedButtonRow
import com.sergokuzneczow.core.system_components.choice_segments.MultiChoiceStrategy
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.PageFilter

@Composable
internal fun CategoriesChips(
    startValue: PageFilter.PictureCategories,
    selectedValue: (value: PageFilter.PictureCategories) -> Unit,
) {
    val options: List<MultiChoice> = listOf(
        MultiChoice(
            label = "General",
            selected = startValue.general,
        ),
        MultiChoice(
            label = "Anime",
            selected = startValue.anime,
        ),
        MultiChoice(
            label = "People",
            selected = startValue.people,
        ),
    )
    Text(
        text = stringResource(R.string.purities_chips),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding)
    )
    PixelsMultiChoiceSegmentedButtonRow(
        options = options,
        onCheckedChange = { selectedChips ->
            val selectedCategories = PageFilter.PictureCategories(
                general = selectedChips[0],
                anime = selectedChips[1],
                people = selectedChips[2],
            )
            selectedValue.invoke(selectedCategories)
        },
        modifier = Modifier.padding(top = Dimensions.Padding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
        multiChoiceStrategy = MultiChoiceStrategy.NOT_EMPTY,
    )
}