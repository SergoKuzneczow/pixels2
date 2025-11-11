package com.sergokuzneczow.home.impl.ui

import androidx.compose.runtime.Composable
import com.sergokuzneczow.home.impl.HomeListUiState
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

@Composable
internal fun HomeScreen(
    uiState: HomeListUiState,
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    onSelectQuery: (PageQuery, PageFilter) -> Unit,
    nextPage: () -> Unit,
) {
    when (uiState) {
        is HomeListUiState.Loading -> onChangeProgressBar.invoke(true)

        is HomeListUiState.Success -> {
            onChangeProgressBar.invoke(false)
            HomeList(
                standardQuery = uiState.standardQuery,
                suggestedQueriesPages = uiState.suggestedQueriesPages,
                onItemClick = onSelectQuery,
                nextPage = nextPage
            )
        }
    }
}