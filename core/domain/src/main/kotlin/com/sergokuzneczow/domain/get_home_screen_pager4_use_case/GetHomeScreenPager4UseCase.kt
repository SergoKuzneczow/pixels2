package com.sergokuzneczow.domain.get_home_screen_pager4_use_case

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
    ): Flow<IPixelsPager4.Answer<PictureWithRelations?>> {
        pixelsPager = IPixelsPager4.Builder(
            coroutineScope = coroutineScope,
            sourceDataBlock = { pageNumber, _ -> dataSource(pageNumber) },
            getActualDataBlock = { pageNumber, pageSize -> getActualData(pageNumber, pageSize) },
            setActualDataBlock = { pageNumber, _, new -> cachingData(pageNumber, new) },
        )
            .setPageSize(12)
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
        log(tag = "GetHomeScreenPager4UseCase", level = Level.INFO) { "dataSource(); pageNumber=$pageNumber" }
        return pageRepositoryApi.getPicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER)
    }

    private suspend fun getActualData(pageNumber: Int, pageSize: Int): List<PictureWithRelations>? {
        log(tag = "GetHomeScreenPager4UseCase", level = Level.INFO) { "getActualData(); pageNumber=$pageNumber" }
        val timePageLoad: Long? = pageRepositoryApi.getPageLoadTime(pageNumber, PAGE_QUERY, PAGE_FILTER)
        val shiftCurrentTime: Long = System.currentTimeMillis().minus(SIX_HOURS)
        return if (timePageLoad == null) {
            pageRepositoryApi.getActualPicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER, pageSize)
        } else if (timePageLoad < shiftCurrentTime) {
            if (pageNumber == 1) pageRepositoryApi.deletePages(PAGE_QUERY, PAGE_FILTER)
            pageRepositoryApi.getActualPicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER, pageSize)
        } else null
    }

    private suspend fun cachingData(pageNumber: Int, actual: List<PictureWithRelations>) {
        log(tag = "GetHomeScreenPager4UseCase", level = Level.INFO) { "cachingData(); pageNumber=$pageNumber" }
        pageRepositoryApi.clearAndInsertPicturesWithRelations(actual, pageNumber, PAGE_QUERY, PAGE_FILTER)
    }
}