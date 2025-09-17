package com.sergokuzneczow.database.impl.framework.impl

import com.sergokuzneczow.database.impl.framework.RoomHandler
import com.sergokuzneczow.database.impl.framework.dao.PageDao
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PictureLocalModel
import com.sergokuzneczow.database.impl.framework.models.toPageQueryLocalModel
import com.sergokuzneczow.database.impl.framework.models.toPictureCategoriesLocalModel
import com.sergokuzneczow.database.impl.framework.models.toPictureColorLocalModel
import com.sergokuzneczow.database.impl.framework.models.toPictureOrderLocalModel
import com.sergokuzneczow.database.impl.framework.models.toPicturePuritiesLocalModel
import com.sergokuzneczow.database.impl.framework.models.toPictureSortingLocalModel
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

internal class PageLocalSource private constructor(val pageDao: PageDao) {

    private companion object {
    }

    internal constructor(roomHandler: RoomHandler) : this(roomHandler.providePageDao())

    internal suspend fun setPageGetKey(
        pageNumber: Int = 1,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Long? {
        val pageQuery = pageQuery.toPageQueryLocalModel()
        val sorting = pageFilter.pictureSorting.toPictureSortingLocalModel()
        val order = pageFilter.pictureOrder.toPictureOrderLocalModel()
        val purities = pageFilter.picturePurities.toPicturePuritiesLocalModel()
        val categories = pageFilter.pictureCategories.toPictureCategoriesLocalModel()
        val color = pageFilter.pictureColor.toPictureColorLocalModel()
        val page = PageLocalModel(
            loadTime = System.currentTimeMillis(),
            number = pageNumber,
            query =  Json.encodeToString(pageQuery),
            sorting = Json.encodeToString(sorting),
            order = Json.encodeToString(order),
            purities = Json.encodeToString(purities),
            categories = Json.encodeToString(categories),
            color = Json.encodeToString(color),
        )
        return pageDao.insertOrIgnorePageReturnPageKey(page)
    }

    internal suspend fun getPage(pageKey: Long): PageLocalModel {
        return pageDao.getPages(pageKey).firstOrNull() ?: throw IllegalStateException("Page column for key=$pageKey not found.")
    }

    internal suspend fun getPageLoadTimeOrNull(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Long? {
        val pageQuery = pageQuery.toPageQueryLocalModel()
        val sorting = pageFilter.pictureSorting.toPictureSortingLocalModel()
        val order = pageFilter.pictureOrder.toPictureOrderLocalModel()
        val purities = pageFilter.picturePurities.toPicturePuritiesLocalModel()
        val categories = pageFilter.pictureCategories.toPictureCategoriesLocalModel()
        val color = pageFilter.pictureColor.toPictureColorLocalModel()
        return pageDao.queryPageLoadTime(
            pageNumber = pageNumber,
            query =  Json.encodeToString(pageQuery),
            sorting = Json.encodeToString(sorting),
            order = Json.encodeToString(order),
            purities = Json.encodeToString(purities),
            categories = Json.encodeToString(categories),
            color = Json.encodeToString(color),
        )
    }

    internal fun getPicturesPage(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): Flow<List<PictureLocalModel>> {
        val pageQuery = pageQuery.toPageQueryLocalModel()
        val sorting = pageFilter.pictureSorting.toPictureSortingLocalModel()
        val order = pageFilter.pictureOrder.toPictureOrderLocalModel()
        val purities = pageFilter.picturePurities.toPicturePuritiesLocalModel()
        val categories = pageFilter.pictureCategories.toPictureCategoriesLocalModel()
        val color = pageFilter.pictureColor.toPictureColorLocalModel()
        return pageDao.queryPageWithPictures(
            pageNumber = pageNumber,
            query =  Json.encodeToString(pageQuery),
            sorting = Json.encodeToString(sorting),
            order = Json.encodeToString(order),
            purities = Json.encodeToString(purities),
            categories = Json.encodeToString(categories),
            color = Json.encodeToString(color),
        ).map {
            val pageWithPicture: PageLocalModel.PageWithPictures? = it.firstOrNull()
            pageWithPicture?.pictures?.sortingPicturesByPositionOnPage(pageWithPicture.page.key) ?: emptyList()
        }
    }

    internal fun getPicturesWithRelationsPage(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): Flow<List<PictureLocalModel.PictureWithRelations>> {
        val pageQuery = pageQuery.toPageQueryLocalModel()
        val sorting = pageFilter.pictureSorting.toPictureSortingLocalModel()
        val order = pageFilter.pictureOrder.toPictureOrderLocalModel()
        val purities = pageFilter.picturePurities.toPicturePuritiesLocalModel()
        val categories = pageFilter.pictureCategories.toPictureCategoriesLocalModel()
        val color = pageFilter.pictureColor.toPictureColorLocalModel()
        return pageDao.queryPageWithPicturesWithRelations(
            pageNumber = pageNumber,
            query =  Json.encodeToString(pageQuery),
            sorting = Json.encodeToString(sorting),
            order = Json.encodeToString(order),
            purities = Json.encodeToString(purities),
            categories = Json.encodeToString(categories),
            color = Json.encodeToString(color),
        ).map {
            val pageWithPicture: PageLocalModel.PageWithPicturesWithRelations? = it.firstOrNull()
            pageWithPicture?.picturesWithRelations?.sortingPicturesWithRelationByPositionOnPage(pageWithPicture.page.key) ?: emptyList()
        }
    }

    internal suspend fun deletePages(pageQuery: PageQuery, pageFilter: PageFilter) {
        val pageQuery = pageQuery.toPageQueryLocalModel()
        val sorting = pageFilter.pictureSorting.toPictureSortingLocalModel()
        val order = pageFilter.pictureOrder.toPictureOrderLocalModel()
        val purities = pageFilter.picturePurities.toPicturePuritiesLocalModel()
        val categories = pageFilter.pictureCategories.toPictureCategoriesLocalModel()
        val color = pageFilter.pictureColor.toPictureColorLocalModel()
        pageDao.deletePage(
            query =  Json.encodeToString(pageQuery),
            sorting = Json.encodeToString(sorting),
            order = Json.encodeToString(order),
            purities = Json.encodeToString(purities),
            categories = Json.encodeToString(categories),
            color = Json.encodeToString(color),
        )
    }

    internal suspend fun clearAndInsertPictures(
        pictures: List<PictureLocalModel>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ) {
        val pageQuery = pageQuery.toPageQueryLocalModel()
        val sorting = pageFilter.pictureSorting.toPictureSortingLocalModel()
        val order = pageFilter.pictureOrder.toPictureOrderLocalModel()
        val purities = pageFilter.picturePurities.toPicturePuritiesLocalModel()
        val categories = pageFilter.pictureCategories.toPictureCategoriesLocalModel()
        val color = pageFilter.pictureColor.toPictureColorLocalModel()
        val page = PageLocalModel(
            key = 0,
            number = pageNumber,
            query =  Json.encodeToString(pageQuery),
            sorting = Json.encodeToString(sorting),
            order = Json.encodeToString(order),
            purities = Json.encodeToString(purities),
            categories = Json.encodeToString(categories),
            color = Json.encodeToString(color),
            loadTime = System.currentTimeMillis()
        )
        val pageWithPictures = PageLocalModel.PageWithPictures(
            page = page,
            pictures = pictures
        )
        pageDao.insertOrReplacePagePictures(pageWithPictures)
    }

    internal suspend fun clearAndInsertPicturesWithRelations(
        pictureWithRelations: List<PictureLocalModel.PictureWithRelations>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ) {
        val pageQuery = pageQuery.toPageQueryLocalModel()
        val sorting = pageFilter.pictureSorting.toPictureSortingLocalModel()
        val order = pageFilter.pictureOrder.toPictureOrderLocalModel()
        val purities = pageFilter.picturePurities.toPicturePuritiesLocalModel()
        val categories = pageFilter.pictureCategories.toPictureCategoriesLocalModel()
        val color = pageFilter.pictureColor.toPictureColorLocalModel()
        val page = PageLocalModel(
            key = 0,
            number = pageNumber,
            query =Json.encodeToString(pageQuery),
            sorting = Json.encodeToString(sorting),
            order = Json.encodeToString(order),
            purities = Json.encodeToString(purities),
            categories = Json.encodeToString(categories),
            color = Json.encodeToString(color),
            loadTime = System.currentTimeMillis()
        )
        val pageWithPicturesWithRelations = PageLocalModel.PageWithPicturesWithRelations(
            page = page,
            picturesWithRelations = pictureWithRelations,
        )
        pageDao.insertOrReplacePageWithPicturesWithRelations(pageWithPicturesWithRelations)
    }

    private fun List<PictureLocalModel>.sortingPicturesByPositionOnPage(pageKey: Long): List<PictureLocalModel> {
        val picturesPositions: List<String> = pageDao.getPictureKeysSortedByPosition(pageKey)
        val sortedPictures: List<PictureLocalModel> = buildList {
            picturesPositions.onEach { pictureKey ->
                var position = 0
                val lastPosition: Int = this@sortingPicturesByPositionOnPage.size
                while (position < lastPosition) {
                    if (pictureKey == this@sortingPicturesByPositionOnPage[position].key) {
                        add(this@sortingPicturesByPositionOnPage[position])
                        position = lastPosition
                    } else position++
                }
            }
        }
        return sortedPictures
    }

    private fun List<PictureLocalModel.PictureWithRelations>.sortingPicturesWithRelationByPositionOnPage(pageKey: Long): List<PictureLocalModel.PictureWithRelations> {
        val picturesPositions: List<String> = pageDao.getPictureKeysSortedByPosition(pageKey)
        val sortedPictures: List<PictureLocalModel.PictureWithRelations> = buildList {
            picturesPositions.onEach { pictureKey ->
                var position = 0
                val lastPosition: Int = this@sortingPicturesWithRelationByPositionOnPage.size
                while (position < lastPosition) {
                    if (pictureKey == this@sortingPicturesWithRelationByPositionOnPage[position].picture.key) {
                        add(this@sortingPicturesWithRelationByPositionOnPage[position])
                        position = lastPosition
                    } else position++
                }
            }
        }
        return sortedPictures
    }
}