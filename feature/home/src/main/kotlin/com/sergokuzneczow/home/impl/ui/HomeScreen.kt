package com.sergokuzneczow.home.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.home.impl.HomeListUiState
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

@Composable
internal fun HomeScreen(
    homeListUiState: HomeListUiState,
    itemClick: (PageQuery, PageFilter) -> Unit,
    nextPage: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        HomeList(
            homeListUiState = homeListUiState,
            itemClick = itemClick,
            nextPage = nextPage,
        )
    }
}