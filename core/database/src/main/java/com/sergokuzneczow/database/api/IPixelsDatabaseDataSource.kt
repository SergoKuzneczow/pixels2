package com.sergokuzneczow.database.api

import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.Tag
import kotlinx.coroutines.flow.Flow

public interface IPixelsDatabaseDataSource {

    public suspend fun setPageGetKey(
        pageNumber: Int = 1,
        pageQuery: PageQuery = PageQuery.Empty(),
        pageFilter: PageFilter
    ): Long?

    public suspend fun getPageLoadTimeOrNull(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.Empty(),
        pageFilter: PageFilter
    ): Long?

    public fun getPicturesPage(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.Empty(),
        pageFilter: PageFilter,
    ): Flow<List<Picture>>

    public fun getPicturesWithRelationsPage(
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.Empty(),
        pageFilter: PageFilter,
    ): Flow<List<Picture>>

    public suspend fun deletePages(pageQuery: PageQuery = PageQuery.Empty(), pageFilter: PageFilter)

    public suspend fun clearAndInsertPictures(
        pictures: List<Picture>,
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.Empty(),
        pageFilter: PageFilter,
    )

    public suspend fun clearAndInsertPicturesWithRelations(
        pictureWithRelations: List<Picture>,
        pageNumber: Int,
        pageQuery: PageQuery = PageQuery.Empty(),
        pageFilter: PageFilter,
    )

    public fun getPictureByKey(pictureKey: String): Flow<List<Picture>>

    public fun getPictureWithRelationByKey(pictureKey: String): Flow<List<Picture>>

    public suspend fun setPicture(picture: Picture)

    public suspend fun setPicture(picture: Picture, tags: List<Tag>, colors: List<Color>)

}