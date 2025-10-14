package com.sergokuzneczow.domain.get_first_page_key_use_case

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.PageRepositoryApi
import jakarta.inject.Inject

public class GetFirstPageKeyUseCase @Inject constructor(
    private val pageRepositoryApi: PageRepositoryApi,
) {

    public suspend fun execute(
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter = PageFilter.DEFAULT,
    ): Long? {
        return pageRepositoryApi.getPageKey(
            pageQuery = pageQuery,
            pageFilter = pageFilter,
        )
    }
}