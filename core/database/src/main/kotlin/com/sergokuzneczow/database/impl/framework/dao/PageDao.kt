package com.sergokuzneczow.database.impl.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.PageWithPictures
import com.sergokuzneczow.database.impl.framework.entities.PageLocalModel.PageWithPicturesWithRelations
import com.sergokuzneczow.database.impl.framework.entities.PagePictureCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
public interface PageDao : PagePictureDao {

    @Insert(onConflict = IGNORE)
    public fun insertOrIgnorePage(column: PageLocalModel)

    @Query(
        "select * " +
                "from ${PageLocalModel.PAGES_TABLE_NAME} " +
                "where ${PageLocalModel.QUERY_COLUMN_NAME}=:query " +
                "and ${PageLocalModel.SORTING_COLUMN_NAME}=:sorting " +
                "and ${PageLocalModel.ORDER_COLUMN_NAME}=:order " +
                "and ${PageLocalModel.PURITIES_COLUMN_NAME}=:purities " +
                "and ${PageLocalModel.CATEGORIES_COLUMN_NAME}=:categories " +
                "and ${PageLocalModel.COLOR_COLUMN_NAME}=:color"
    )
    public suspend fun getPages(
        query: String,
        sorting: String,
        order: String,
        purities: String,
        categories: String,
        color: String,
    ): List<PageLocalModel>

    @Query(
        "select * " +
                "from ${PageLocalModel.PAGES_TABLE_NAME} " +
                "where ${PageLocalModel.PAGE_NUMBER_COLUMN_NAME}=:pageNumber " +
                "and ${PageLocalModel.QUERY_COLUMN_NAME}=:query " +
                "and ${PageLocalModel.SORTING_COLUMN_NAME}=:sorting " +
                "and ${PageLocalModel.ORDER_COLUMN_NAME}=:order " +
                "and ${PageLocalModel.PURITIES_COLUMN_NAME}=:purities " +
                "and ${PageLocalModel.CATEGORIES_COLUMN_NAME}=:categories " +
                "and ${PageLocalModel.COLOR_COLUMN_NAME}=:color"
    )
    public suspend fun getPages(
        pageNumber: Int,
        query: String,
        sorting: String,
        order: String,
        purities: String,
        categories: String,
        color: String,
    ): List<PageLocalModel>

    @Query(
        "select * " +
                "from ${PageLocalModel.PAGES_TABLE_NAME} " +
                "where ${PageLocalModel.PAGE_NUMBER_COLUMN_NAME}=:pageNumber " +
                "and ${PageLocalModel.QUERY_COLUMN_NAME}=:query " +
                "and ${PageLocalModel.SORTING_COLUMN_NAME}=:sorting " +
                "and ${PageLocalModel.ORDER_COLUMN_NAME}=:order " +
                "and ${PageLocalModel.PURITIES_COLUMN_NAME}=:purities " +
                "and ${PageLocalModel.CATEGORIES_COLUMN_NAME}=:categories " +
                "and ${PageLocalModel.COLOR_COLUMN_NAME}=:color"
    )
    public fun getPagesAsFlow(
        pageNumber: Int,
        query: String,
        sorting: String,
        order: String,
        purities: String,
        categories: String,
        color: String,
    ): Flow<List<PageLocalModel>>

    @Query(
        "select ${PageLocalModel.LOAD_TIME_COLUMN_NAME} " +
                "from ${PageLocalModel.PAGES_TABLE_NAME} " +
                "where ${PageLocalModel.PAGE_NUMBER_COLUMN_NAME}=:pageNumber " +
                "and ${PageLocalModel.QUERY_COLUMN_NAME}=:query " +
                "and ${PageLocalModel.SORTING_COLUMN_NAME}=:sorting " +
                "and ${PageLocalModel.ORDER_COLUMN_NAME}=:order " +
                "and ${PageLocalModel.PURITIES_COLUMN_NAME}=:purities " +
                "and ${PageLocalModel.CATEGORIES_COLUMN_NAME}=:categories " +
                "and ${PageLocalModel.COLOR_COLUMN_NAME}=:color"
    )
    public suspend fun queryPageLoadTime(
        pageNumber: Int,
        query: String,
        sorting: String,
        order: String,
        purities: String,
        categories: String,
        color: String,
    ): Long?

    @Query(
        "delete " +
                "from ${PageLocalModel.PAGES_TABLE_NAME} " +
                "where ${PageLocalModel.QUERY_COLUMN_NAME}=:query " +
                "and ${PageLocalModel.SORTING_COLUMN_NAME}=:sorting " +
                "and ${PageLocalModel.ORDER_COLUMN_NAME}=:order " +
                "and ${PageLocalModel.PURITIES_COLUMN_NAME}=:purities " +
                "and ${PageLocalModel.CATEGORIES_COLUMN_NAME}=:categories " +
                "and ${PageLocalModel.COLOR_COLUMN_NAME}=:color"
    )
    public suspend fun deletePage(
        query: String,
        sorting: String,
        order: String,
        purities: String,
        categories: String,
        color: String,
    )

    @Transaction
    @Query(
        "select * " +
                "from ${PageLocalModel.PAGES_TABLE_NAME} " +
                "where ${PageLocalModel.PAGE_NUMBER_COLUMN_NAME}=:pageNumber " +
                "and ${PageLocalModel.QUERY_COLUMN_NAME}=:query " +
                "and ${PageLocalModel.SORTING_COLUMN_NAME}=:sorting " +
                "and ${PageLocalModel.ORDER_COLUMN_NAME}=:order " +
                "and ${PageLocalModel.PURITIES_COLUMN_NAME}=:purities " +
                "and ${PageLocalModel.CATEGORIES_COLUMN_NAME}=:categories " +
                "and ${PageLocalModel.COLOR_COLUMN_NAME}=:color"
    )
    public fun queryPageWithPictures(
        pageNumber: Int,
        query: String,
        sorting: String,
        order: String,
        purities: String,
        categories: String,
        color: String,
    ): Flow<List<PageWithPictures>>

    @Transaction
    @Query(
        "select * " +
                "from ${PageLocalModel.PAGES_TABLE_NAME} " +
                "where ${PageLocalModel.PAGE_NUMBER_COLUMN_NAME}=:pageNumber " +
                "and ${PageLocalModel.QUERY_COLUMN_NAME}=:query " +
                "and ${PageLocalModel.SORTING_COLUMN_NAME}=:sorting " +
                "and ${PageLocalModel.ORDER_COLUMN_NAME}=:order " +
                "and ${PageLocalModel.PURITIES_COLUMN_NAME}=:purities " +
                "and ${PageLocalModel.CATEGORIES_COLUMN_NAME}=:categories " +
                "and ${PageLocalModel.COLOR_COLUMN_NAME}=:color"
    )
    public fun queryPageWithPicturesWithRelations(
        pageNumber: Int,
        query: String,
        sorting: String,
        order: String,
        purities: String,
        categories: String,
        color: String,
    ): Flow<List<PageWithPicturesWithRelations>>

    @Transaction
    public suspend fun insertOrIgnorePageReturnPageKey(page: PageLocalModel): Long? {
        insertOrIgnorePage(page)
        return getPages(
            page.number,
            query = page.query,
            sorting = page.sorting,
            order = page.order,
            purities = page.purities,
            categories = page.categories,
            color = page.color
        ).firstOrNull()?.key
    }

    @Transaction
    public suspend fun insertOrReplacePagePictures(pageWithPictures: PageWithPictures) {
        insertOrIgnorePage(pageWithPictures.page)
        insertOrIgnorePictures(pageWithPictures.pictures)

        /**
         * Все страницы, соответствующие параметрам. */
        val equalPageKeys = getPages(
            query = pageWithPictures.page.query,
            sorting = pageWithPictures.page.sorting,
            order = pageWithPictures.page.order,
            purities = pageWithPictures.page.purities,
            categories = pageWithPictures.page.categories,
            color = pageWithPictures.page.color
        ).map { it.key }
        val picturesKeys = pageWithPictures.pictures.map { it.key }

        /**
         * Удаление изображений, которые связаны со страницами, которые соответствуют параметрам. */
        deletePagePictureCrossRefByPageAndPictureKeys(equalPageKeys, picturesKeys)

        /**
         * Ключ страницы, которая соответствует параметрам. */
        val pageKey = getPages(
            pageWithPictures.page.number,
            query = pageWithPictures.page.query,
            sorting = pageWithPictures.page.sorting,
            order = pageWithPictures.page.order,
            purities = pageWithPictures.page.purities,
            categories = pageWithPictures.page.categories,
            color = pageWithPictures.page.color
        ).first().key

        /**
         * Удаление всех изображений, связанных с найденом ключем страницы. */
        deletePagePictureCrossRefByPageKey(pageKey)

        val pagePictureCrossRefs: List<PagePictureCrossRef> = picturesKeys.mapIndexed { index, value ->
            PagePictureCrossRef(
                pageKey = pageKey,
                pictureKey = value,
                position = index,
            )
        }
        insertOrIgnorePageAndPictureCrossRef(pagePictureCrossRefs)
    }

    @Transaction
    public suspend fun insertOrReplacePageWithPicturesWithRelations(pageWithPictures: PageWithPicturesWithRelations) {
        val picturesKeys: List<String> = insertOrIgnorePictureWithRelationsReturnPictureKey(pageWithPictures.picturesWithRelations)
        val pageKey: Long? = insertOrIgnorePageReturnPageKey(pageWithPictures.page)
        pageKey?.let { deletePagePictureCrossRefByPageKey(pageKey) }

        /**
         * Все страницы, соответствующие параметрам. */
        val pagesOfSimilarSignatures: List<Long> = getPages(
            query = pageWithPictures.page.query,
            sorting = pageWithPictures.page.sorting,
            order = pageWithPictures.page.order,
            purities = pageWithPictures.page.purities,
            categories = pageWithPictures.page.categories,
            color = pageWithPictures.page.color
        ).map { it.key }
        /**
         * Удаление изображений, которые связаны со страницами, которые соответствуют параметрам. */
        deletePagePictureCrossRefByPageAndPictureKeys(pagesOfSimilarSignatures, picturesKeys)

        pageKey?.let { pageKey ->
            val pagePictureCrossRefs: List<PagePictureCrossRef> = picturesKeys.mapIndexed { index, value ->
                PagePictureCrossRef(
                    pageKey = pageKey,
                    pictureKey = value,
                    position = index,
                )
            }
            insertOrIgnorePageAndPictureCrossRef(pagePictureCrossRefs)
        }
    }
}