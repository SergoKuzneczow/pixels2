package com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case

import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.utilities.logger.Level
import com.sergokuzneczow.utilities.logger.log
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onEach

public class GetSuitablePicturesScreenPager4UseCase @Inject constructor(
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

    private var pixelsPager: IPixelsPager4<Picture>? = null

    public fun execute(
        coroutineScope: CoroutineScope,
        pageQuery: PageQuery = PAGE_QUERY,
        pageFilter: PageFilter = PAGE_FILTER,
    ): Flow<IPixelsPager4.Answer<Picture?>> {
        pixelsPager = IPixelsPager4.Builder(
            coroutineScope = coroutineScope,
            sourceDataBlock = { pageNumber, _ -> dataSource(pageNumber, pageQuery, pageFilter) },
            getActualDataBlock = { pageNumber, pageSize -> getActualData(pageNumber, pageQuery, pageFilter) },
            setActualDataBlock = { pageNumber, _, new -> cachingData(pageNumber, new, pageQuery, pageFilter) },
        )
            .setRequestLastPageNumber { pageRepositoryApi.getLastActualPageNumber(pageQuery, pageFilter) }
            .setPageSize(24)
            .setStartStrategy(IPixelsPager4.StartStrategy.INSTANTLY)
            .setPlaceholderStrategy(IPixelsPager4.PlaceholdersStrategy.WITHOUT)
            .setLoadStrategy(IPixelsPager4.LoadStrategy.SEQUENTIALLY)
            .build()

        return pixelsPager?.getPages()
            ?.onEach { (_, meta) -> if (!meta.empty) pixelsPager?.changePlaceholdersStrategy(IPixelsPager4.PlaceholdersStrategy.WITH) }
            ?: throw IllegalStateException("Property pager must initialize.")
    }

    public fun nextPage() {
        pixelsPager?.nextPage()
    }

    private fun dataSource(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): Flow<List<Picture>> {
        return pageRepositoryApi.getPictures(pageNumber, pageQuery, pageFilter)
    }

    private suspend fun getActualData(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ): List<Picture>? {
        return pageRepositoryApi.getActualPictures(pageNumber, pageQuery, pageFilter)
    }

    private suspend fun cachingData(
        pageNumber: Int,
        actual: List<Picture>,
        pageQuery: PageQuery,
        pageFilter: PageFilter,
    ) {
        pageRepositoryApi.clearAndInsertPictures(actual, pageNumber, pageQuery, pageFilter)
    }
}