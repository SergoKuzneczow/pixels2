package com.sergokuzneczow.home.impl

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

internal interface HomeListIntent {
    data object NextPage : HomeListIntent
    data class SelectQuery(val pageQuery: PageQuery, val pageFilter: PageFilter) : HomeListIntent
}