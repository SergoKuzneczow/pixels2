package com.sergokuzneczow.bottom_sheet_page_filter.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.bottom_sheet_page_filter.R
import com.sergokuzneczow.bottom_sheet_page_filter.impl.BottomSheetPageFilterScreenState
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.PageFilterItem
import com.sergokuzneczow.core.system_components.buttons.PixelsSurfaceButton
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.PageFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetPageFilterScreen(
    state: BottomSheetPageFilterScreenState,
    onSelectSorting: (options: List<PageFilterItem<PageFilter.PictureSorting>>) -> Unit,
    onSelectOrder: (options: List<PageFilterItem<PageFilter.PictureOrder>>) -> Unit,
    onSelectPurities: (options: List<PageFilterItem<PageFilter.PicturePurities>>) -> Unit,
    onSelectCategories: (options: List<PageFilterItem<PageFilter.PictureCategories>>) -> Unit,
    onDone: () -> Unit,
    onClose: () -> Unit,
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    when (state) {
        is BottomSheetPageFilterScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) { PixelsProgressIndicator(Dimensions.SmallProgressBarSize) }
        }

        is BottomSheetPageFilterScreenState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                ModalBottomSheet(
                    onDismissRequest = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                            onClose.invoke()
                        }
                    },
                    sheetState = bottomSheetState,
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = Dimensions.ContentPadding)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = stringResource(R.string.bottom_sheet_title),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = Dimensions.LargePadding, start = Dimensions.LargePadding)
                            )
                            SortingChoice(
                                options = state.sorting,
                                onSelect = onSelectSorting,
                            )
                            OrderChoice(
                                options = state.order,
                                onSelect = onSelectOrder,
                            )
                            PuritiesChoice(
                                options = state.purities,
                                onSelect = onSelectPurities,
                            )
                            CategoriesChoice(
                                options = state.categories,
                                onSelect = onSelectCategories,
                            )
                            Box(modifier = Modifier.fillMaxWidth()) {
                                PixelsSurfaceButton(
                                    text = stringResource(R.string.done),
                                    onClick = onDone,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(Dimensions.LargePadding)
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}