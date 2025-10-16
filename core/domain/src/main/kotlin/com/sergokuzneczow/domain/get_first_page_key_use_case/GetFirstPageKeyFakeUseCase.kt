package com.sergokuzneczow.domain.get_first_page_key_use_case

import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PageRepositoryApi
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

public class GetFirstPageKeyFakeUseCase @Inject constructor(
    pageRepositoryApi: PageRepositoryApi = MockPageRepositoryApi,
) : GetFirstPageKeyUseCase(pageRepositoryApi) {

    private var returnValue: Long? = null

    override suspend fun execute(pageQuery: PageQuery, pageFilter: PageFilter): Long? {
        return returnValue
    }

    public fun setReturnValue(value: Long?) {
        returnValue = value
    }
}

private object MockPageRepositoryApi : PageRepositoryApi {
    override suspend fun getPageKey(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): Long? = TODO("Not yet implemented")
    override suspend fun getPage(pageKey: Long): Page = TODO("Not yet implemented")
    override suspend fun getPageLoadTime(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): Long? = TODO("Not yet implemented")
    override fun getPictures(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Flow<List<Picture>> = TODO("Not yet implemented")

    override fun getPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Flow<List<PictureWithRelations>> = TODO("Not yet implemented")

    override suspend fun deletePages(pageQuery: PageQuery, pageFilter: PageFilter) = TODO("Not yet implemented")
    override suspend fun getLastActualPageNumber(pageQuery: PageQuery, pageFilter: PageFilter): Int = TODO("Not yet implemented")
    override suspend fun getActualPictures(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): List<Picture> = TODO("Not yet implemented")

    override suspend fun getActualPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): List<PictureWithRelations> = TODO("Not yet implemented")

    override suspend fun getActualPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
        pageSize: Int
    ): List<PictureWithRelations> = TODO("Not yet implemented")

    override suspend fun clearAndInsertPictures(
        pageData: List<Picture>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ) = TODO("Not yet implemented")

    override suspend fun clearAndInsertPicturesWithRelations(
        pageData: List<PictureWithRelations>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ) = TODO("Not yet implemented")

    override suspend fun updatePictures(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): List<Picture> = TODO("Not yet implemented")

    override suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): List<PictureWithRelations> = TODO("Not yet implemented")

    override suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
        pageSize: Int
    ): List<PictureWithRelations> = TODO("Not yet implemented")

}