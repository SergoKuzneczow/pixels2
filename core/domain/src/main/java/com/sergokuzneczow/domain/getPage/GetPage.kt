package com.sergokuzneczow.domain.getPage

import com.sergokuzneczow.models.Page
import com.sergokuzneczow.repository.api.PageRepositoryApi
import jakarta.inject.Inject

public class GetPage @Inject constructor(
    private val pageRepositoryApi: PageRepositoryApi,
) {

    public suspend fun execute(pageKey: Long): Page {
        return pageRepositoryApi.getPage(pageKey)
    }
}