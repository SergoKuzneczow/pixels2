package com.sergokuzneczow.home.impl

import androidx.compose.ui.graphics.vector.ImageVector
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.models.Tag
import java.util.TreeMap

internal sealed interface HomeListUiState {

    class Loading private constructor(
        val standardQuery: List<StandardQuery>,
    ) : HomeListUiState {
        constructor() : this(standardQuery = StandardQuery.standardQueries)
    }

    class Success private constructor(
        val standardQuery: List<StandardQuery>,
        val suggestedQueriesPages: List<SuggestedQueriesPage>
    ) : HomeListUiState {
        constructor(suggestedQueriesPages: List<SuggestedQueriesPage>) : this(
            standardQuery = StandardQuery.standardQueries,
            suggestedQueriesPages = suggestedQueriesPages
        )
    }

    data class StandardQuery(
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

    data class SuggestedQueriesPage(
        val items: List<SuggestedQuery?>,
    )

    data class SuggestedQuery(
        val description: String,
        val previewPath: String,
        val pageQuery: PageQuery,
        val pageFilter: PageFilter,
    )
}

internal fun TreeMap<Int, List<PictureWithRelations?>>.toSuggestedQueriesPages(): List<HomeListUiState.SuggestedQueriesPage> {
    return this.entries.map { HomeListUiState.SuggestedQueriesPage(it.value.toSuggestedQueriesNew()) }
}

internal fun IPixelsPager4.Answer<PictureWithRelations?>.toSuggestedQueriesPages(): List<HomeListUiState.SuggestedQueriesPage> {
    return this.pages.entries.map { HomeListUiState.SuggestedQueriesPage(it.value.data.toSuggestedQueriesNew()) }
}

internal fun List<PictureWithRelations?>.toSuggestedQueriesNew(): List<HomeListUiState.SuggestedQuery?> {
    var typePosition = 0
    return this.mapIndexed { index, picture ->
        when (typePosition) {
            3 -> {
                typePosition++
                val colorName: String? =
                    picture?.let { if (it.colors.isNotEmpty()) it.colors[0].name else null }
                if (colorName != null) {
                    HomeListUiState.SuggestedQuery(
                        description = "Color $colorName",
                        previewPath = picture.picture.original,
                        pageQuery = PageQuery.Empty(),
                        pageFilter = PageFilter.DEFAULT.copy(pictureColor = PageFilter.PictureColor(colorName)),
                    )
                } else null
            }

            6 -> {
                /**
                 * Обнуление типа специального Item для зацикливания.*/
                typePosition = 0
                val tag: Tag? =
                    picture?.let { if (it.tags.isNotEmpty() && it.tags.size > 5) it.tags[4] else if (it.tags.isNotEmpty()) it.tags[0] else null }
                if (tag != null) {
                    HomeListUiState.SuggestedQuery(
                        description = "#${tag.name}",
                        previewPath = picture.picture.original,
                        pageQuery = PageQuery.Tag(
                            tagKey = tag.id,
                            description = tag.name
                        ),
                        pageFilter = PageFilter.DEFAULT,
                    )
                } else null
            }

            else -> {
                typePosition++
                val queryKeyWord: String? =
                    picture?.let { if (it.tags.isNotEmpty() && it.tags.size > 5) it.tags[4].name else if (it.tags.isNotEmpty()) it.tags[0].name else null }
                if (queryKeyWord != null) {
                    HomeListUiState.SuggestedQuery(
                        description = queryKeyWord.replaceFirstChar { it.uppercase() },
                        previewPath = picture.picture.original,
                        pageQuery = PageQuery.KeyWord(word = queryKeyWord),
                        pageFilter = PageFilter.DEFAULT,
                    )
                } else null
            }
        }
    }
}