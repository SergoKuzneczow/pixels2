package com.sergokuzneczow.domain.get_home_screen_pager_use_case

import com.sergokuzneczow.domain.pager.PixelsPager
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.utilities.logger.log
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

public class GetHomeScreenPagerUseCase @Inject constructor(
    private val pageRepositoryApi: PageRepositoryApi,
) {
    private companion object {
        private val PAGE_QUERY = PageQuery.Empty
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

    private var pixelsPager: PixelsPager<PictureWithRelations>? = null

    public fun execute(
        coroutineScope: CoroutineScope,
        loading: () -> Unit,
        completed: (lastPage: Int, isEmpty: Boolean) -> Unit,
        error: (throwable: Throwable) -> Unit,
    ): SharedFlow<PixelsPager.Pages<PictureWithRelations?>> {
        pixelsPager = PixelsPager.Builder(
            coroutineScope = coroutineScope,
            sourceData = { pageNumber, pageSize -> dataSource(pageNumber, pageSize) },
            syncData = { pageNumber, pageSize -> syncDataSource(pageNumber, pageSize) }
        )
            .setPageDownloadStartedCallback { loading.invoke() }
            .setPageSyncCompletedCallback { _, _, lastPage, isEmpty -> completed.invoke(lastPage, isEmpty) }
            .setSourceDataExceptionCallback { _, t -> error.invoke(t) }
            .setSyncDataExceptionCallback { _, t -> error.invoke(t) }
            .setPageSize(8)
            .setWithPlaceholder(true)
            .setStartStrategy(PixelsPager.StartStrategy.START_INSTANTLY)
            .build()
        return pixelsPager?.mapFlow() ?: throw IllegalStateException("Property pixelsPager must initialize.")
    }

    public fun nextPage() {
        pixelsPager?.nextPage()
    }

    private suspend fun dataSource(pageNumber: Int, pageSize: Int): Flow<List<PictureWithRelations>> {
        return pageRepositoryApi.getPicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER)
    }

    private suspend fun syncDataSource(pageNumber: Int, pageSize: Int): List<PictureWithRelations>? {
        log(tag = "GetHomeScreenPagerUseCase") { "syncDataSource(); enter point" }
        val timePageLoad: Long? = pageRepositoryApi.getPageLoadTime(pageNumber, PAGE_QUERY, PAGE_FILTER)
        val currentTime: Long = System.currentTimeMillis()
        val shiftCurrentTime: Long = System.currentTimeMillis().minus(SIX_HOURS)
        log(tag = "HomeFragmentViewModel") { "syncSourcePageData(); timePageLoad=$timePageLoad, currentTime=$currentTime, shiftCurrentTime=$shiftCurrentTime" }
        if (timePageLoad == null) {
            log(tag = "HomeFragmentViewModel") { "syncSourcePageData(); updatePicturesWithRelations() when null" }
            return pageRepositoryApi.updatePicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER, pageSize)
        } else if (timePageLoad < shiftCurrentTime) {
            log(tag = "HomeFragmentViewModel") { "syncSourcePageData(); updatePicturesWithRelations() when timePageLoad more currentTime}" }
            pageRepositoryApi.deletePages(PAGE_QUERY, PAGE_FILTER)
            return pageRepositoryApi.updatePicturesWithRelations(pageNumber, PAGE_QUERY, PAGE_FILTER, pageSize)
        }
        return null
    }
}