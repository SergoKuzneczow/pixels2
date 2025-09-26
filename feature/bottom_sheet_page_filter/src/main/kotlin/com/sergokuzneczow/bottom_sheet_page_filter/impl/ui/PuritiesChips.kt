package com.sergokuzneczow.bottom_sheet_page_filter.impl.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.bottom_sheet_page_filter.R
import com.sergokuzneczow.core.system_components.choice_segments.MultiChoice
import com.sergokuzneczow.core.system_components.choice_segments.MultiChoiceColorsAccent
import com.sergokuzneczow.core.system_components.choice_segments.MultiChoiceStrategy
import com.sergokuzneczow.core.system_components.choice_segments.PixelsMultiChoiceSegmentedButtonRow
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.PageFilter

@Composable
internal fun PuritiesChips(
    startValue: PageFilter.PicturePurities,
    selectedValue: (value: PageFilter.PicturePurities) -> Unit,
) {
    val options: List<MultiChoice> = listOf(
        MultiChoice(
            label = "Sfw",
            selected = startValue.sfw,
        ),
        MultiChoice(
            label = "Sketchy*",
            selected = startValue.sketchy,
        ),
        MultiChoice(
            label = "Nsfw*",
            selected = startValue.nsfw,
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
            val selectedPurities = PageFilter.PicturePurities(
                sfw = selectedChips[0],
                sketchy = selectedChips[1],
                nsfw = selectedChips[2],
            )
            selectedValue.invoke(selectedPurities)
        },
        modifier = Modifier.padding(top = Dimensions.Padding, start = Dimensions.LargePadding, end = Dimensions.LargePadding),
        multiChoiceStrategy = MultiChoiceStrategy.NOT_EMPTY,
        colorAccentPredicate = { index, value ->
            when (index) {
                1 -> MultiChoiceColorsAccent.WARNING
                2 -> MultiChoiceColorsAccent.DANGEROUS
                else -> MultiChoiceColorsAccent.STANDARD
            }
        },
    )

    Text(
        text = stringResource(R.string.nsfw_content),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    )
}