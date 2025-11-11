package com.sergokuzneczow.domain.get_home_screen_pager4_use_case

import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PageRepositoryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

public class GetHomeScreenPager4FakeUseCase() : GetHomeScreenPager4UseCase(pageRepositoryApi = pageRepositoryApiMock) {

    private val pagesSource: MutableSharedFlow<IPixelsPager4.Answer<PictureWithRelations?>> = MutableSharedFlow()

    override fun execute(coroutineScope: CoroutineScope): Flow<IPixelsPager4.Answer<PictureWithRelations?>> = pagesSource

    override fun nextPage() {
    }

    public suspend fun emitAnswer(answer: IPixelsPager4.Answer<PictureWithRelations?>) {
        pagesSource.emit(answer)
    }
}

private val pageRepositoryApiMock = object : PageRepositoryApi {
    override suspend fun getPageKey(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): Long? {
        TODO("Not yet implemented")
    }

    override suspend fun getPage(pageKey: Long): Page {
        TODO("Not yet implemented")
    }

    override suspend fun getPageLoadTime(pageNumber: Int, pageQuery: PageQuery, pageFilter: PageFilter): Long? {
        TODO("Not yet implemented")
    }

    override fun getPictures(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): Flow<List<Picture>> {
        TODO("Not yet implemented")
    }

    override fun getPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): Flow<List<PictureWithRelations>> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePages(pageQuery: PageQuery, pageFilter: PageFilter) {
        TODO("Not yet implemented")
    }

    override suspend fun getLastActualPageNumber(pageQuery: PageQuery, pageFilter: PageFilter): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getActualPictures(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): List<Picture> {
        TODO("Not yet implemented")
    }

    override suspend fun getActualPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): List<PictureWithRelations> {
        TODO("Not yet implemented")
    }

    override suspend fun getActualPicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
        pageSize: Int,
    ): List<PictureWithRelations> {
        TODO("Not yet implemented")
    }

    override suspend fun clearAndInsertPictures(
        pageData: List<Picture>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun clearAndInsertPicturesWithRelations(
        pageData: List<PictureWithRelations>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePictures(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): List<Picture> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): List<PictureWithRelations> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePicturesWithRelations(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
        pageSize: Int,
    ): List<PictureWithRelations> {
        TODO("Not yet implemented")
    }
}