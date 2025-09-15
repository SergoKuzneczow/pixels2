package com.sergokuzneczow.repository.api

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import kotlinx.coroutines.flow.Flow

public interface PageRepositoryApi {

    public suspend fun getPageLoadTime(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    ): Long?

    public fun getPictures(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    ): Flow<List<Picture>>

    public fun getPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    ): Flow<List<PictureWithRelations>>

    public suspend fun deletePages(
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    )

    public suspend fun updatePictures(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    )

    public suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    )

    public suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
        pageSize: Int,
    )

    public suspend fun getLastActualPageNumber(
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    ): Int
}