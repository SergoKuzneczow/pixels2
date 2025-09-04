package com.sergokuzneczow.network.api

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.Tag

public interface IPixelsNetworkDataSource {

    public suspend fun getPictureTags(pictureKey: String): List<Tag>

    public suspend fun getPicturesPage(
        pageNumber: Int,
        pageFilter: PageFilter,
    ): List<Picture>

    public suspend fun getPicturesPage(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): List<Picture>

    public suspend fun getLastPageNumber(
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Int

    public suspend fun getPicture(pictureKey: String): Picture
}