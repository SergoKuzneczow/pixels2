package com.sergokuzneczow.home.impl.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

internal data class StandardQuery(
    val description: String,
    val vectorIcon: ImageVector,
    val pageQuery: PageQuery,
    val pageFilter: PageFilter,
) {

    internal companion object {
        internal val standardQueries: List<StandardQuery> = listOf(
            StandardQuery(
                description = "New",
                vectorIcon = PixelsIcons.new,
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureSorting = PageFilter.PictureSorting.DATE_ADDED)
            ),
            StandardQuery(
                description = "Bests",
                vectorIcon = PixelsIcons.topList,
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureSorting = PageFilter.PictureSorting.TOP_LIST)
            ),
            StandardQuery(
                description = "Loved",
                vectorIcon = PixelsIcons.favorites,
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureSorting = PageFilter.PictureSorting.FAVORITES)
            ),
            StandardQuery(
                description = "Views",
                vectorIcon = PixelsIcons.views,
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureSorting = PageFilter.PictureSorting.VIEWS)
            ),
        )
    }
}