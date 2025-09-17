package com.sergokuzneczow.home.impl.ui

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.PictureWithRelations
import com.sergokuzneczow.models.Tag

internal sealed interface SuggestedQueriesUiState {

    data object Empty : SuggestedQueriesUiState

    class Default private constructor(val suggestedQueries: List<SuggestedQuery?>) : SuggestedQueriesUiState {
        constructor() : this(suggestedQueries = emptyList())
    }

    data class Success(val suggestedQueries: List<SuggestedQuery?>) : SuggestedQueriesUiState

    data class SuggestedQuery(
        val description: String,
        val previewPath: String,
        val pageQuery: PageQuery,
        val pageFilter: PageFilter,
    )
}

internal fun List<PictureWithRelations?>.toSuggestedQueries(): List<SuggestedQueriesUiState.SuggestedQuery?> {
    var typePosition = 0
    return this.mapIndexed { index, picture ->
        when (typePosition) {
            3 -> {
                typePosition++
                val colorName: String? =
                    picture?.let { if (it.colors.isNotEmpty()) it.colors[0].name else null }
                if (colorName != null) {
                    SuggestedQueriesUiState.SuggestedQuery(
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
                    SuggestedQueriesUiState.SuggestedQuery(
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
                    SuggestedQueriesUiState.SuggestedQuery(
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
