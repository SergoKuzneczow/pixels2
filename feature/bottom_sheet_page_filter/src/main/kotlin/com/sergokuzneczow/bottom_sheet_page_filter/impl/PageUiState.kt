package com.sergokuzneczow.bottom_sheet_page_filter.impl

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

internal sealed interface PageUiState {

    data object Loading : PageUiState

    data class Success(
        val pageQuery: PageQuery,
        val pageFilter: PageFilter
    ) : PageUiState
}