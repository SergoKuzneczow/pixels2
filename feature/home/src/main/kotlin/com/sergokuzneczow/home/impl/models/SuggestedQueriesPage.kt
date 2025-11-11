package com.sergokuzneczow.home.impl.models

import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.models.Tag

internal data class SuggestedQueriesPage(
    val items: List<SuggestedQuery?>,
) {

    data class SuggestedQuery(
        val description: String,
        val previewPath: String,
        val pageQuery: PageQuery,
        val pageFilter: PageFilter,
    )
}

internal fun IPixelsPager4.Answer<PictureWithRelations?>.toSuggestedQueriesPages(): List<SuggestedQueriesPage> {
    return this.pages.entries.map { SuggestedQueriesPage(it.value.data.toSuggestedQueriesNew()) }
}

internal fun List<PictureWithRelations?>.toSuggestedQueriesNew(): List<SuggestedQueriesPage.SuggestedQuery?> {
    var typePosition = 0
    return this.mapIndexed { _, picture ->
        when (typePosition) {
            2 -> {
                typePosition++
                val colorName: String? = picture?.let { if (it.colors.isNotEmpty()) it.colors[0].name else null }
                if (colorName != null) {
                    SuggestedQueriesPage.SuggestedQuery(
                        description = "Color $colorName",
                        previewPath = picture.picture.original,
                        pageQuery = PageQuery.Empty,
                        pageFilter = PageFilter.DEFAULT.copy(pictureColor = PageFilter.PictureColor(colorName)),
                    )
                } else null
            }

            5 -> {
                /**
                 * Обнуление типа специального Item для зацикливания.*/
                typePosition = 0
                val tag: Tag? = picture?.let { if (it.tags.isNotEmpty() && it.tags.size > 5) it.tags[4] else if (it.tags.isNotEmpty()) it.tags[0] else null }
                if (tag != null) {
                    SuggestedQueriesPage.SuggestedQuery(
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
                val queryKeyWord: String? = picture?.let { if (it.tags.isNotEmpty() && it.tags.size > 5) it.tags[4].name else if (it.tags.isNotEmpty()) it.tags[0].name else null }
                if (queryKeyWord != null) {
                    SuggestedQueriesPage.SuggestedQuery(
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