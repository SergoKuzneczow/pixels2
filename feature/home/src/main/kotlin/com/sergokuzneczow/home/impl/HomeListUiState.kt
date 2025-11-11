package com.sergokuzneczow.home.impl

import com.sergokuzneczow.home.impl.models.StandardQuery
import com.sergokuzneczow.home.impl.models.SuggestedQueriesPage

internal sealed interface HomeListUiState {

    data object Loading : HomeListUiState

    class Success private constructor(
        val standardQuery: List<StandardQuery>,
        val suggestedQueriesPages: List<SuggestedQueriesPage>?,
    ) : HomeListUiState {
        constructor(suggestedQueriesPages: List<SuggestedQueriesPage>?) : this(
            standardQuery = StandardQuery.standardQueries,
            suggestedQueriesPages = suggestedQueriesPages
        )
    }

    data class OpenSelectedQuery(val pageKey: Long) : HomeListUiState
}