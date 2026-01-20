package com.sergokuzneczow.home.impl.ui

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import com.sergokuzneczow.home.impl.HomeScreenState
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

@Composable
internal fun HomeScreen(
    state: HomeScreenState,
    imageLoader: ImageLoader,
    onSelectPage: (PageQuery, PageFilter) -> Unit,
    onNextPage: () -> Unit,
) {
    when (state) {
        is HomeScreenState.Loading -> {
            HomeList(
                imageLoader = imageLoader,
                standardQuery = state.standardQuery,
                suggestedQueriesPages = null,
                isLoadingNextPage = true,
                onItemClick = onSelectPage,
                onNextPage = onNextPage,
            )
        }

        is HomeScreenState.Success -> {
            HomeList(
                imageLoader = imageLoader,
                standardQuery = state.standardQuery,
                suggestedQueriesPages = state.suggestedQueriesPages,
                isLoadingNextPage = true,
                onItemClick = onSelectPage,
                onNextPage = onNextPage,
            )
        }
    }
}