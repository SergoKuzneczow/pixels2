package com.sergokuzneczow.home.impl.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

internal data class StandardQuery(
    val description: String,
    val icon: ImageVector,
    val pageQuery: PageQuery,
    val pageFilter: PageFilter,
) {
    internal companion object {
        internal val standardQueries: List<StandardQuery> = listOf(
            StandardQuery(
                description = "New",
                icon = PixelsIcons.new,
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureSorting = PageFilter.PictureSorting.DATE_ADDED)
            ),
            StandardQuery(
                description = "Bests",
                icon = PixelsIcons.topList,
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureSorting = PageFilter.PictureSorting.TOP_LIST)
            ),
            StandardQuery(
                description = "Loved",
                icon = PixelsIcons.favorites,
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureSorting = PageFilter.PictureSorting.FAVORITES)
            ),
            StandardQuery(
                description = "Views",
                icon = PixelsIcons.views,
                pageQuery = PageQuery.DEFAULT,
                pageFilter = PageFilter.DEFAULT.copy(pictureSorting = PageFilter.PictureSorting.VIEWS)
            ),
        )
    }
}