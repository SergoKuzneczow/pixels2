package com.sergokuzneczow.network.impl

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.models.Tag
import com.sergokuzneczow.network.api.PixelsNetworkDataSourceApi
import com.sergokuzneczow.network.impl.retrofit.api.PictureApiImpl
import com.sergokuzneczow.network.impl.retrofit.api.PicturesPageApiImpl
import com.sergokuzneczow.network.impl.retrofit.models.toPictureWithRelations
import com.sergokuzneczow.network.impl.retrofit.models.toPictures
import com.sergokuzneczow.network.impl.retrofit.models.toTags
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
public class PixelsNetworkDataSourceImpl @Inject constructor() : PixelsNetworkDataSourceApi {
    private val picturesPageApi: PicturesPageApiImpl by lazy { PicturesPageApiImpl() }
    private val pictureApi: PictureApiImpl by lazy { PictureApiImpl() }

    public override suspend fun getPictureTags(pictureKey: String): List<Tag> = pictureApi.getPictureTags(pictureKey).toTags()

    public override suspend fun getPicturesPage(pageNumber: Int, pageFilter: PageFilter): List<Picture> =
        picturesPageApi.getPicturesPage(pageNumber, pageFilter).toPictures()

    public override suspend fun getPicturesPage(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): List<Picture> =
        picturesPageApi.getPicturesPage(pageNumber, pageQuery, pageFilter).toPictures()

    public override suspend fun getLastPageNumber(pageQuery: PageQuery, pageFilter: PageFilter): Int =
        picturesPageApi.getLastPageNumber(pageQuery, pageFilter)

    public override suspend fun getPicture(pictureKey: String): PictureWithRelations = pictureApi.getPicture(pictureKey).toPictureWithRelations()
}