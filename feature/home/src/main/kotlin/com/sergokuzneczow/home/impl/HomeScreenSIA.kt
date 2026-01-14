package com.sergokuzneczow.home.impl

import com.sergokuzneczow.home.impl.models.StandardQuery
import com.sergokuzneczow.home.impl.models.SuggestedQueriesPage
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

internal sealed interface HomeScreenState {
    data object Loading : HomeScreenState {
        val standardQuery: List<StandardQuery> = StandardQuery.standardQueries
        val isLoadingNextPage: Boolean = true
    }

    data class Success(
        val suggestedQueriesPages: List<SuggestedQueriesPage>?,
        val isLoadingNextPage: Boolean = false,
    ) : HomeScreenState {
        val standardQuery: List<StandardQuery> = StandardQuery.standardQueries
    }
}

internal interface HomeScreenIntent {
    data object NextPage : HomeScreenIntent
    data class UpdatePages(val suggestedQueriesPages: List<SuggestedQueriesPage>?) : HomeScreenIntent
    data class SelectQuery(val pageQuery: PageQuery, val pageFilter: PageFilter) : HomeScreenIntent
}

internal interface HomeScreenSideEffect {
    data class ShowPages(val pageKey: Long) : HomeScreenSideEffect
}