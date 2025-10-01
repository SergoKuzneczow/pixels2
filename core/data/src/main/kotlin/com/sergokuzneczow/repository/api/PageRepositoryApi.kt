package com.sergokuzneczow.repository.api

import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import kotlinx.coroutines.flow.Flow

public interface PageRepositoryApi {

    public suspend fun getPageKey(
        pageNumber: Int = 1,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    ): Long?

    public suspend fun getPage(
        pageKey: Long
    ): Page

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
    ): List<Picture>

    public suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    ): List<PictureWithRelations>

    public suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
        pageSize: Int,
    ): List<PictureWithRelations>

    public suspend fun getLastActualPageNumber(
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    ): Int

    public suspend fun getActualPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
    ): List<PictureWithRelations>

    public suspend fun getActualPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.DEFAULT,
        pageFilter: PageFilter,
        pageSize: Int,
    ): List<PictureWithRelations>

    public suspend fun clearAndInsertPicturesWithRelations(
        pageData: List<PictureWithRelations>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    )
}