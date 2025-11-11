package com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case

import com.sergokuzneczow.domain.pager.PixelsPager
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.utilities.logger.log
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

public class GetSuitablePicturesScreenPagerUseCase @Inject constructor(
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
    }

    private var pixelsPager: PixelsPager<Picture>? = null

    public fun execute(
        coroutineScope: CoroutineScope,
        pageQuery: PageQuery = PAGE_QUERY,
        pageFilter: PageFilter = PAGE_FILTER,
        loading: () -> Unit,
        completed: (isLastPage: Boolean, isEmpty: Boolean) -> Unit,
        error: (throwable: Throwable) -> Unit,
    ): SharedFlow<PixelsPager.Pages<Picture?>> {
        pixelsPager = PixelsPager.Builder(
            coroutineScope = coroutineScope,
            sourceData = { pageNumber, _ -> dataSource(pageNumber, pageQuery, pageFilter) },
            syncData = { pageNumber, _ -> syncDataSource(pageNumber, pageQuery, pageFilter) }
        )
            .setRequestLastPageNumber { pageRepositoryApi.getLastActualPageNumber(pageQuery, pageFilter) }
            .setPageDownloadStartedCallback { loading.invoke() }
            .setPageSyncCompletedCallback { pageNumber, firstPage, lastPage, isEmpty -> completed.invoke(pageNumber == lastPage, isEmpty) }
            .setSourceDataExceptionCallback { _, t -> error.invoke(t) }
            .setSyncDataExceptionCallback { _, t -> error.invoke(t) }
            .setPageSize(24)
            .setWithPlaceholder(true)
            .setStartStrategy(PixelsPager.StartStrategy.START_INSTANTLY)
            .build()
        return pixelsPager?.mapFlow() ?: throw IllegalStateException("Property pager must initialize.")
    }

    public fun nextPage() {
        pixelsPager?.nextPage()
    }

    private suspend fun dataSource(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): Flow<List<Picture>> {
        return pageRepositoryApi.getPictures(
            pageNumber = pageNumber,
            pageQuery = pageQuery,
            pageFilter = pageFilter,
        )
    }

    private suspend fun syncDataSource(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): List<Picture> {
        log(tag = "GetSuitablePicturesScreenPagerUseCase") { "syncDataSource(); enter point" }
        return pageRepositoryApi.updatePictures(pageNumber, pageQuery, pageFilter)
    }
}