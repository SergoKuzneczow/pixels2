package com.sergokuzneczow.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
internal fun HomeScreen(
    suggestedQueriesUiState: SuggestedQueriesUiState,
    suggestedQueryClick: () -> Unit,
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
                suggestedQueryClick = {},
                nextPage = {},
            )
        }
    }
}