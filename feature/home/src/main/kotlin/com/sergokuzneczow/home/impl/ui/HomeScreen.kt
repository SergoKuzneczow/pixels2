package com.sergokuzneczow.home.impl.ui

import androidx.compose.runtime.Composable
import com.sergokuzneczow.home.impl.HomeListUiState
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

@Composable
internal fun HomeScreen(
    uiState: HomeListUiState,
    onSelectQuery: (PageQuery, PageFilter) -> Unit,
    nextPage: () -> Unit,
    navigateToSuitablePicturesDestination: (Long) -> Unit,
) {
    when (uiState) {
        is HomeListUiState.Loading -> {

        }

        is HomeListUiState.Success -> {
            HomeList(
                standardQuery = uiState.standardQuery,
                suggestedQueriesPages = uiState.suggestedQueriesPages,
                onItemClick = onSelectQuery,
                nextPage = nextPage
            )
        }

        is HomeListUiState.OpenSelectedQuery -> navigateToSuitablePicturesDestination.invoke(uiState.pageKey)
    }
}