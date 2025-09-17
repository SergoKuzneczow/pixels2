package com.sergokuzneczow.home.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

@Composable
internal fun HomeScreen(
    suggestedQueriesUiState: SuggestedQueriesUiState,
    suggestedQueryClick: (PageQuery, PageFilter) -> Unit,
    nextPage: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SuggestedQueriesList(
            suggestedQueriesUiState = suggestedQueriesUiState,
            suggestedQueryClick = suggestedQueryClick,
            nextPage = nextPage,
        )
    }
}

@ThemePreviews
@Composable
public fun HomeScreenPreview() {
    PixelsTheme {
        Surface {
            HomeScreen(
                suggestedQueriesUiState = SuggestedQueriesUiState.Success(emptyList()),
                suggestedQueryClick = { _, _ -> },
                nextPage = {},
            )
        }
    }
}