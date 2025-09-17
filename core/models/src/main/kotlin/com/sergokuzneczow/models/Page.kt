package com.sergokuzneczow.models

public data class Page(
    val key: Long,
    val loadTime: Long,
    val number: Int,
    val query: PageQuery,
    val filter: PageFilter,
)
