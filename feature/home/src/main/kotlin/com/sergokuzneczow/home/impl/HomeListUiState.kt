package com.sergokuzneczow.home.impl

import com.sergokuzneczow.home.impl.models.StandardQuery
import com.sergokuzneczow.home.impl.models.SuggestedQueriesPage

internal sealed interface HomeListUiState {

    class Loading private constructor(
        val standardQuery: List<StandardQuery>,
    ) : HomeListUiState {
        constructor() : this(standardQuery = StandardQuery.standardQueries)
    }

    class Success private constructor(
        val standardQuery: List<StandardQuery>,
        val suggestedQueriesPages: List<SuggestedQueriesPage>,
    ) : HomeListUiState {
        constructor(suggestedQueriesPages: List<SuggestedQueriesPage>) : this(
            standardQuery = StandardQuery.standardQueries,
            suggestedQueriesPages = suggestedQueriesPages
        )
    }
}