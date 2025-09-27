package com.sergokuzneczow.repository.impl

import com.sergokuzneczow.database.api.PixelsDatabaseDataSourceApi
import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.network.api.PixelsNetworkDataSourceApi
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.utilities.logger.log
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

public class PageRepositoryImpl @Inject constructor(
    private val databaseApi: PixelsDatabaseDataSourceApi,
    private val networkApi: PixelsNetworkDataSourceApi,
) : PageRepositoryApi {

    override suspend fun getPageKey(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): Long? {
        return databaseApi.setPageGetKey(pageNumber, pageQuery, pageFilter)
    }

    override suspend fun getPage(pageKey: Long): Page {
        return databaseApi.getPage(pageKey)
    }

    override suspend fun getPageLoadTime(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): Long? {
        log(tag = "PageRepositoryImpl") { "getPageLoadTime(); enter point; pageNumber=$pageNumber, pageQuery=$pageQuery, pageFilter=$pageFilter" }
        return databaseApi.getPageLoadTimeOrNull(pageNumber, pageQuery, pageFilter)
    }

    override fun getPictures(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): Flow<List<Picture>> =
        databaseApi.getPicturesPage(pageNumber, pageQuery, pageFilter)

    override fun getPicturesWithRelations(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): Flow<List<PictureWithRelations>> =
        databaseApi.getPicturesWithRelationsPage(pageNumber, pageQuery, pageFilter)

    override suspend fun deletePages(pageQuery: PageQuery, pageFilter: PageFilter) {
        databaseApi.deletePages(pageQuery, pageFilter)
    }

    override suspend fun updatePictures(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): List<Picture> {
        val remoteAnswer: List<Picture> = networkApi.getPicturesPage(pageNumber, pageQuery, pageFilter)
        databaseApi.clearAndInsertPictures(remoteAnswer, pageNumber, pageQuery, pageFilter)
        return remoteAnswer
    }

    override suspend fun updatePicturesWithRelations(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): List<PictureWithRelations> {
        val actualPictures: List<Picture> = networkApi.getPicturesPage(pageNumber, pageQuery, pageFilter)
        val actualKeys: List<String> = actualPictures.map { it.key }
        val actualPicturesWithRelations: MutableList<PictureWithRelations> = mutableListOf()
        var position = 0
        while (position < actualKeys.size) {
            val actualPictureWithRelation: PictureWithRelations = networkApi.getPicture(actualKeys[position])
            actualPicturesWithRelations.add(actualPictureWithRelation)
            position++
            delay(50)
        }
        databaseApi.clearAndInsertPicturesWithRelations(
            pictureWithRelations = actualPicturesWithRelations,
            pageNumber = pageNumber,
            pageQuery = pageQuery,
            pageFilter = pageFilter,
        )
        return actualPicturesWithRelations
    }

    override suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
        pageSize: Int
    ): List<PictureWithRelations> {
        log(tag = "PageRepositoryImpl") { "updatePicturesWithRelations(); pageNumber=$pageNumber, pageQuery=$pageQuery, pageFilter=$pageFilter enter point" }
        val actualPictures: List<Picture> = networkApi.getPicturesPage(pageNumber, pageQuery, pageFilter)
        val actualKeys: List<String> = if (actualPictures.size > pageSize) actualPictures.subList(0, pageSize).map { it.key } else actualPictures.map { it.key }
        val actualPicturesWithRelations: MutableList<PictureWithRelations> = mutableListOf()
        var position = 0
        while (position < actualKeys.size) {
            val actualPictureWithRelation: PictureWithRelations = networkApi.getPicture(actualKeys[position])
            log(tag = "PageRepositoryImpl") { "updatePicturesWithRelations(); actualPictureWithRelation=$actualPictureWithRelation" }
            actualPicturesWithRelations.add(actualPictureWithRelation)
            position++
            delay(50)
        }
        databaseApi.clearAndInsertPicturesWithRelations(
            pictureWithRelations = actualPicturesWithRelations,
            pageNumber = pageNumber,
            pageQuery = pageQuery,
            pageFilter = pageFilter,
        )
        return actualPicturesWithRelations
    }

    override suspend fun getLastActualPageNumber(pageQuery: PageQuery, pageFilter: PageFilter): Int = networkApi.getLastPageNumber(pageQuery, pageFilter)
}