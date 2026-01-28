package com.sergokuzneczow.bottom_sheet_page_filter.impl

import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.PageFilterItem
import com.sergokuzneczow.models.PageFilter

internal sealed interface BottomSheetPageFilterScreenState {
    data object Loading : BottomSheetPageFilterScreenState
    data class Success(
        val sorting: List<PageFilterItem<PageFilter.PictureSorting>>,
        val order: List<PageFilterItem<PageFilter.PictureOrder>>,
        val purities: List<PageFilterItem<PageFilter.PicturePurities>>,
        val categories: List<PageFilterItem<PageFilter.PictureCategories>>,
    ) : BottomSheetPageFilterScreenState
}

internal sealed interface BottomSheetPageFilterScreenIntent {
    data class SetStartInformation(
        val sorting: List<PageFilterItem<PageFilter.PictureSorting>>,
        val order: List<PageFilterItem<PageFilter.PictureOrder>>,
        val purities: List<PageFilterItem<PageFilter.PicturePurities>>,
        val categories: List<PageFilterItem<PageFilter.PictureCategories>>,
    ) : BottomSheetPageFilterScreenIntent

    data class ChangeSelectedSorting(val sorting: List<PageFilterItem<PageFilter.PictureSorting>>) : BottomSheetPageFilterScreenIntent
    data class ChangeSelectedOrder(val order: List<PageFilterItem<PageFilter.PictureOrder>>) : BottomSheetPageFilterScreenIntent
    data class ChangeSelectedPurities(val purities: List<PageFilterItem<PageFilter.PicturePurities>>) : BottomSheetPageFilterScreenIntent
    data class ChangeSelectedCategories(val categories: List<PageFilterItem<PageFilter.PictureCategories>>) : BottomSheetPageFilterScreenIntent
    data object Done : BottomSheetPageFilterScreenIntent
}

internal sealed interface BottomSheetPageFilterScreenSideEffect {
    data class OnDone(val pageKey: Long) : BottomSheetPageFilterScreenSideEffect
}