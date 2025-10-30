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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.bottom_sheet_page_filter.R
import com.sergokuzneczow.bottom_sheet_page_filter.impl.PageUiState
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.system_components.buttons.PixelsSurfaceButton
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetPageFilterScreen(
    pageUiState: PageUiState,
    doneNewPageFilter: (pageQuery: PageQuery, pageFilter: PageFilter) -> Unit,
    closeDialog: () -> Unit,
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(modifier = Modifier.fillMaxSize()) {
        when (pageUiState) {
            is PageUiState.Loading -> {
                PixelsCircularProgressIndicator()
            }

            is PageUiState.Success -> {
                val selectedSorting: MutableState<PageFilter.PictureSorting> = remember { mutableStateOf(pageUiState.pageFilter.pictureSorting) }
                val selectedOrder: MutableState<PageFilter.PictureOrder> = remember { mutableStateOf(pageUiState.pageFilter.pictureOrder) }
                val selectedPurities: MutableState<PageFilter.PicturePurities> = remember { mutableStateOf(pageUiState.pageFilter.picturePurities) }
                val selectedCategories: MutableState<PageFilter.PictureCategories> = remember { mutableStateOf(pageUiState.pageFilter.pictureCategories) }

                ModalBottomSheet(
                    onDismissRequest = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                            closeDialog.invoke()
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
                                startValue = pageUiState.pageFilter.pictureSorting,
                                selectedValue = { selectedSorting.value = it },
                            )
                            OrderChoice(
                                startValue = pageUiState.pageFilter.pictureOrder,
                                selectedValue = { selectedOrder.value = it },
                            )
                            PuritiesChips(
                                startValue = pageUiState.pageFilter.picturePurities,
                                selectedValue = { selectedPurities.value = it }
                            )
                            CategoriesChips(
                                startValue = pageUiState.pageFilter.pictureCategories,
                                selectedValue = { selectedCategories.value = it }
                            )
                            Box(modifier = Modifier.fillMaxWidth()) {
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
                )
            }
        }
    }
}