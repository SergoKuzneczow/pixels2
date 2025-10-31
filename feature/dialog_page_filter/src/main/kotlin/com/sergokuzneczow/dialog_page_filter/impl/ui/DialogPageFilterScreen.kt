package com.sergokuzneczow.dialog_page_filter.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.system_components.chip_segments.FilterSegmentColorsAccent
import com.sergokuzneczow.core.system_components.chip_segments.PixelsFilterSegment
import com.sergokuzneczow.core.system_components.chip_segments.FilterSegmentChip
import com.sergokuzneczow.core.system_components.chip_segments.FilterSegmentStrategy
import com.sergokuzneczow.core.system_components.chip_segments.PixelsSingleFilterSegment
import com.sergokuzneczow.core.system_components.chip_segments.SingleFilterChip
import com.sergokuzneczow.core.system_components.buttons.PixelsSurfaceButton
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.dialog_page_filter.R
import com.sergokuzneczow.dialog_page_filter.impl.PageUiState
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery


@Composable
internal fun DialogPageFilterScreen(
    pageUiState: PageUiState,
    doneNewPageFilter: (pageQuery: PageQuery, pageFilter: PageFilter) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Dimensions.PixelsShape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.dialog_page_filter_screen_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimensions.Padding)
        )

        when (pageUiState) {
            is PageUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .height(96.dp)
                        .fillMaxWidth()
                ) {
                    PixelsProgressIndicator(Dimensions.SmallProgressBarSize)
                }
            }

            is PageUiState.Success -> {
                val selectedSorting: MutableState<PageFilter.PictureSorting> = remember { mutableStateOf(pageUiState.pageFilter.pictureSorting) }
                val selectedOrder: MutableState<PageFilter.PictureOrder> = remember { mutableStateOf(pageUiState.pageFilter.pictureOrder) }
                val selectedPurities: MutableState<PageFilter.PicturePurities> = remember { mutableStateOf(pageUiState.pageFilter.picturePurities) }
                val selectedCategories: MutableState<PageFilter.PictureCategories> = remember { mutableStateOf(pageUiState.pageFilter.pictureCategories) }

                SortingChips(
                    startValue = pageUiState.pageFilter.pictureSorting,
                    selectedValue = { value -> selectedSorting.value = value },
                )
                OrderChips(
                    startValue = pageUiState.pageFilter.pictureOrder,
                    selectedValue = { value -> selectedOrder.value = value }
                )
                PuritiesChips(
                    startValue = pageUiState.pageFilter.picturePurities,
                    selectedValue = { selectedValue -> selectedPurities.value = selectedValue }
                )
                CategoriesChips(
                    startValue = pageUiState.pageFilter.pictureCategories,
                    selectedValue = { selectedValue -> selectedCategories.value = selectedValue }
                )
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PixelsSurfaceButton(
                        text = stringResource(R.string.done),
                        onClick = {
                            doneNewPageFilter.invoke(
                                pageUiState.pageQuery,
                                PageFilter(
                                    pictureSorting = selectedSorting.value,
                                    pictureOrder = selectedOrder.value,
                                    picturePurities = selectedPurities.value,
                                    pictureCategories = selectedCategories.value,
                                    pictureColor = pageUiState.pageFilter.pictureColor,
                                )
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(Dimensions.LargePadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun SortingChips(
    startValue: PageFilter.PictureSorting,
    selectedValue: (sorting: PageFilter.PictureSorting) -> Unit,
) {
    val sortingChips: List<SingleFilterChip<PageFilter.PictureSorting>> = listOf(
        SingleFilterChip(
            title = "Views",
            value = PageFilter.PictureSorting.VIEWS,
        ),
        SingleFilterChip(
            title = "Loved",
            value = PageFilter.PictureSorting.FAVORITES,
        ),
        SingleFilterChip(
            title = "Bests",
            value = PageFilter.PictureSorting.TOP_LIST,
        ),
        SingleFilterChip(
            title = "Date",
            value = PageFilter.PictureSorting.DATE_ADDED,
        ),
    )
    Text(
        text = stringResource(R.string.sorting_chips),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding)
    )
    PixelsSingleFilterSegment(
        chips = sortingChips,
        startValue = startValue,
        selectedValue = selectedValue,
        modifier = Modifier.padding(start = Dimensions.Padding, end = Dimensions.Padding)
    )
}

@Composable
private fun OrderChips(
    startValue: PageFilter.PictureOrder,
    selectedValue: (order: PageFilter.PictureOrder) -> Unit,
) {
    val sortingChips: List<SingleFilterChip<PageFilter.PictureOrder>> = listOf(
        SingleFilterChip(
            title = "Desc",
            value = PageFilter.PictureOrder.DESC,
        ),
        SingleFilterChip(
            title = "Asc",
            value = PageFilter.PictureOrder.ASC,
        ),
    )
    Text(
        text = stringResource(R.string.order_chips),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding)
    )
    PixelsSingleFilterSegment(
        chips = sortingChips,
        startValue = startValue,
        selectedValue = selectedValue,
        modifier = Modifier.padding(start = Dimensions.Padding, end = Dimensions.Padding)
    )
}

@Composable
private fun PuritiesChips(
    startValue: PageFilter.PicturePurities,
    selectedValue: (value: PageFilter.PicturePurities) -> Unit,
) {
    val puritiesChips: List<FilterSegmentChip> = listOf(
        FilterSegmentChip(
            title = "Sfw",
            startState = startValue.sfw,
        ),
        FilterSegmentChip(
            title = "Sketchy*",
            startState = startValue.sketchy,
        ),
        FilterSegmentChip(
            title = "Nsfw*",
            startState = startValue.nsfw,
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
    PixelsFilterSegment(
        chips = puritiesChips,
        selectedValues = { selectedChips ->
            val selectedPurities = PageFilter.PicturePurities(
                sfw = selectedChips[0],
                sketchy = selectedChips[1],
                nsfw = selectedChips[2],
            )
            selectedValue.invoke(selectedPurities)
        },
        modifier = Modifier.padding(start = Dimensions.Padding, end = Dimensions.Padding),
        filterSegmentStrategy = FilterSegmentStrategy.NOT_EMPTY,
        colorAccentPredicate = { index, value ->
            when (index) {
                1 -> FilterSegmentColorsAccent.WARNING
                2 -> FilterSegmentColorsAccent.DANGEROUS
                else -> FilterSegmentColorsAccent.STANDARD
            }
        }
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

@Composable
private fun CategoriesChips(
    startValue: PageFilter.PictureCategories,
    selectedValue: (values: PageFilter.PictureCategories) -> Unit,
) {
    val puritiesChips: List<FilterSegmentChip> = listOf(
        FilterSegmentChip(
            title = "General",
            startState = startValue.general,
        ),
        FilterSegmentChip(
            title = "Anime",
            startState = startValue.anime,
        ),
        FilterSegmentChip(
            title = "People",
            startState = startValue.people,
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
    PixelsFilterSegment(
        chips = puritiesChips,
        selectedValues = { selectedChips ->
            val selectedCategories = PageFilter.PictureCategories(
                general = selectedChips[0],
                anime = selectedChips[1],
                people = selectedChips[2],
            )
            selectedValue.invoke(selectedCategories)
        },
        modifier = Modifier.padding(start = Dimensions.Padding, end = Dimensions.Padding),
        filterSegmentStrategy = FilterSegmentStrategy.NOT_EMPTY,
    )
}

@ThemePreviews
@Composable
private fun DialogPageFilterScreenLoadingPreview() {
    PixelsTheme {
        DialogPageFilterScreen(
            pageUiState = PageUiState.Loading,
            doneNewPageFilter = { _, _ -> },
        )
    }
}

@ThemePreviews
@Composable
private fun DialogPageFilterScreenSuccessPreview() {
    PixelsTheme {
        DialogPageFilterScreen(
            pageUiState = PageUiState.Success(
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT,
            ),
            doneNewPageFilter = { _, _ -> },
        )
    }
}