package com.sergokuzneczow.domain.get_home_screen_pager_use_case

import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.utilities.logger.Level
import com.sergokuzneczow.utilities.logger.log
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

public class GetHomeScreenPager4UseCase @Inject constructor(
    private val pageRepositoryApi: PageRepositoryApi,
) {
    private companion object {
        private val PAGE_QUERY = PageQuery.Empty()
        private val PAGE_FILTER = PageFilter(
            pictureSorting = PageFilter.PictureSorting.RANDOM,
            pictureOrder = PageFilter.PictureOrder.DESC,
            picturePurities = PageFilter.PicturePurities(
                sfw = true,
                sketchy = false,
                nsfw = false,
            ),
            pictureCategories = PageFilter.PictureCategories(
                general = true,
                anime = true,
                people = true
            ),
            pictureColor = PageFilter.PictureColor.ANY,
        )
        private const val SIX_HOURS: Long = 21_600_000
    }

    private var pixelsPager: IPixelsPager4<PictureWithRelations>? = null

    public fun execute(
        coroutineScope: CoroutineScope,
        loading: () -> Unit,
        completed: (lastPage: Int, isEmpty: Boolean) -> Unit,
        error: (throwable: Throwable) -> Unit,
    ): SharedFlow<IPixelsPager4.Answer<PictureWithRelations?>> {
        pixelsPager = IPixelsPager4.Builder(
            coroutineScope = coroutineScope,
            sourceDataBlock = { pageNumber, _ -> dataSource(pageNumber) },
            getActualDataBlock = { pageNumber, pageSize -> getActualData(pageNumber, pageSize) },
            setActualDataBlock = { pageNumber, _, new -> cachingDataData(pageNumber, new) },
        )
//            .setPageDownloadStartedCallback { loading.invoke() }
//            .setPageSyncCompletedCallback { _, _, lastPage, isEmpty -> completed.invoke(lastPage, isEmpty) }
//            .setSourceDataExceptionCallback { _, t -> error.invoke(t) }
//            .setSyncDataExceptionCallback { _, t -> error.invoke(t) }
            .setPageSize(8)
            .setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(IPixelsPager4.PlaceholdersStrategy.WITH)
            .setLoadStrategy(IPixelsPager4.LoadStrategy.SEQUENTIALLY)
            .build()
        return pixelsPager?.getPages() ?: throw IllegalStateException("Property pixelsPager must initialize.")
    }

    public fun nextPage() {
        pixelsPager?.nextPage()
    }

    private fun dataSource(pageNumber: Int): Flow<List<PictureWithRelations>> {
        return pageRepositoryApi.getPicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER)
    }

    private suspend fun getActualData(pageNumber: Int, pageSize: Int): List<PictureWithRelations>? {
        log(tag = "GetHomeScreenPager4UseCase", level = Level.INFO) { "getActualData(); pageNumber=$pageNumber" }
        val timePageLoad: Long? = pageRepositoryApi.getPageLoadTime(pageNumber, PAGE_QUERY, PAGE_FILTER)
        val shiftCurrentTime: Long = System.currentTimeMillis().minus(SIX_HOURS)
        return if (timePageLoad == null) {
            log(tag = "GetHomeScreenPager4UseCase") { "getActualData(); timePageLoad == null" }
            pageRepositoryApi.getActualPicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER, pageSize)
        } else if (timePageLoad < shiftCurrentTime) {
            log(tag = "GetHomeScreenPager4UseCase") { "getActualData(); timePageLoad < shiftCurrentTime" }
            if (pageNumber == 1) pageRepositoryApi.deletePages(PAGE_QUERY, PAGE_FILTER)
            pageRepositoryApi.getActualPicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER, pageSize)
        } else null
    }

    private suspend fun cachingDataData(pageNumber: Int, actual: List<PictureWithRelations>) {
        pageRepositoryApi.clearAndInsertPicturesWithRelations(actual, pageNumber, PAGE_QUERY, PAGE_FILTER)
    }
}