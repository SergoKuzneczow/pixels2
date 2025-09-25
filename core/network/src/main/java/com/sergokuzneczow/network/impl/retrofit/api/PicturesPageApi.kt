package com.sergokuzneczow.network.impl.retrofit.api

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.network.impl.retrofit.RetrofitHandler
import com.sergokuzneczow.network.impl.retrofit.models.PicturesPageRemoteModel
import retrofit2.http.GET
import retrofit2.http.Query


internal interface PicturesPageApi {

    @GET("v1/search")
    suspend fun get(
        @Query("page") pageNumber: Int,
        @Query("sorting") sorting: String,
        @Query("order") order: String,
        @Query("purity") purities: String,
        @Query("categories") categories: String,
    ): PicturesPageRemoteModel

    @GET("v1/search")
    suspend fun getByQuery(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("sorting") sorting: String,
        @Query("order") order: String,
        @Query("purity") purities: String,
        @Query("categories") categories: String,
        @Query("colors") color: String,
    ): PicturesPageRemoteModel

    enum class SortingConstants(internal val value: String) {
        VIEWS("views"),
        RANDOM("random"),
        FAVORITES("favorites"),
        TOP_LIST("toplist"),
        DATE_ADDED("date_added");
    }

    enum class OrderConstants(internal val value: String) {
        DESC("desc"),
        ASC("asc");
    }
}

internal class PicturesPageApiImpl private constructor(
    private val picturesPageApi: PicturesPageApi,
) {

    internal constructor() : this(RetrofitHandler.providePicturesPageApi())

    suspend fun getPicturesPage(
        pageNumber: Int,
        pageFilter: PageFilter,
    ): List<PicturesPageRemoteModel.PicturePreviewRemoteModel> {
        val sorting: String = pageFilter.pictureSorting.toSortingString()
        val order: String = pageFilter.pictureOrder.toOrderString()
        val purities: String = pageFilter.picturePurities.toPuritiesString()
        val categories: String = pageFilter.pictureCategories.toCategoriesString()

        return picturesPageApi.get(
            pageNumber = pageNumber,
            sorting = sorting,
            order = order,
            purities = purities,
            categories = categories,
        ).data
    }

    suspend fun getPicturesPage(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): List<PicturesPageRemoteModel.PicturePreviewRemoteModel> {
        val query: String = pageQuery.toQueryString()
        val sorting: String = pageFilter.pictureSorting.toSortingString()
        val order: String = pageFilter.pictureOrder.toOrderString()
        val purities: String = pageFilter.picturePurities.toPuritiesString()
        val categories: String = pageFilter.pictureCategories.toCategoriesString()
        val color: String = pageFilter.pictureColor.colorName.deleteHashtag()

        return picturesPageApi.getByQuery(
            query = query,
            pageNumber = pageNumber,
            sorting = sorting,
            order = order,
            purities = purities,
            categories = categories,
            color = color,
        ).data
    }

    suspend fun getLastPageNumber(pageQuery: PageQuery, pageFilter: PageFilter): Int {
        val query: String = pageQuery.toQueryString()
        val sorting: String = pageFilter.pictureSorting.toSortingString()
        val order: String = pageFilter.pictureOrder.toOrderString()
        val purities: String = pageFilter.picturePurities.toPuritiesString()
        val categories: String = pageFilter.pictureCategories.toCategoriesString()
        val color: String = pageFilter.pictureColor.colorName.deleteHashtag()

        return picturesPageApi.getByQuery(
            query = query,
            pageNumber = DEFAULT_PAGE_NUMBER,
            sorting = sorting,
            order = order,
            purities = purities,
            categories = categories,
            color = color,
        ).meta.lastPage
    }

    private fun PageFilter.PictureSorting.toSortingString(): String {
        return when (this) {
            PageFilter.PictureSorting.VIEWS -> PicturesPageApi.SortingConstants.VIEWS.value
            PageFilter.PictureSorting.RANDOM -> PicturesPageApi.SortingConstants.RANDOM.value
            PageFilter.PictureSorting.FAVORITES -> PicturesPageApi.SortingConstants.FAVORITES.value
            PageFilter.PictureSorting.TOP_LIST -> PicturesPageApi.SortingConstants.TOP_LIST.value
            PageFilter.PictureSorting.DATE_ADDED -> PicturesPageApi.SortingConstants.DATE_ADDED.value
        }
    }

    private fun PageFilter.PictureOrder.toOrderString(): String {
        return when (this) {
            PageFilter.PictureOrder.DESC -> PicturesPageApi.OrderConstants.DESC.value
            PageFilter.PictureOrder.ASC -> PicturesPageApi.OrderConstants.ASC.value
        }
    }

    private fun PageFilter.PicturePurities.toPuritiesString(): String {
        val sfw: Int = if (this.sfw) 1 else 0
        val sketchy: Int = if (this.sketchy) 1 else 0
        val nsfw: Int = if (this.nsfw) 1 else 0
        return "$sfw$sketchy$nsfw"
    }

    private fun PageFilter.PictureCategories.toCategoriesString(): String {
        val general: Int = if (this.general) 1 else 0
        val anime: Int = if (this.anime) 1 else 0
        val people: Int = if (this.people) 1 else 0
        return "$general$anime$people"
    }


    private fun PageQuery.toQueryString(): String = when (this) {
        is PageQuery.KeyWord -> this.word
        is PageQuery.KeyWords -> this.words.joinToString(separator = "+")
        is PageQuery.Like -> "like:${this.pictureKey}"
        is PageQuery.Empty -> ""
        is PageQuery.Tag -> "id:${this.tagKey}"
    }

    private fun String.deleteHashtag(): String {
        var res: String = ""
        this.forEach { ch -> if (ch != '#') res = res + ch }
        return if (res.length == 6) res else ""
    }

    private companion object {

        private const val DEFAULT_PAGE_NUMBER: Int = 1
    }
}