package com.sergokuzneczow.database.impl.framework.models

import com.sergokuzneczow.models.PageQuery
import kotlinx.serialization.Serializable


@Serializable
public sealed interface PageQueryLocalModel {
    public companion object {
        private const val ANY_QUERY: String = "any_query"
        public val DEFAULT: Empty = Empty()
    }

    @Serializable
    public data class Empty(val value: String = ANY_QUERY) : PageQueryLocalModel

    @Serializable
    public data class KeyWord(val word: String) : PageQueryLocalModel

    @Serializable
    public data class KeyWords(val words: List<String>, val descriptions: List<String>) : PageQueryLocalModel

    @Serializable
    public data class Like(val pictureKey: String, val description: String) : PageQueryLocalModel

    @Serializable
    public data class Tag(val tagKey: Int, val description: String) : PageQueryLocalModel
}

internal fun PageQuery.toPageQueryLocalModel(): PageQueryLocalModel = when (this) {
    is PageQuery.Empty -> PageQueryLocalModel.Empty()
    is PageQuery.KeyWord -> PageQueryLocalModel.KeyWord(this.word)
    is PageQuery.KeyWords -> PageQueryLocalModel.KeyWords(this.words, this.descriptions)
    is PageQuery.Like -> PageQueryLocalModel.Like(pictureKey = this.pictureKey, description = this.description)
    is PageQuery.Tag -> PageQueryLocalModel.Tag(tagKey = this.tagKey, description = this.description)
}

internal fun PageQueryLocalModel.toPageQuery(): PageQuery = when (this) {
    is PageQueryLocalModel.Empty -> PageQuery.Empty
    is PageQueryLocalModel.KeyWord -> PageQuery.KeyWord(this.word)
    is PageQueryLocalModel.KeyWords -> PageQuery.KeyWords(this.words, this.descriptions)
    is PageQueryLocalModel.Like -> PageQuery.Like(pictureKey = this.pictureKey, description = this.description)
    is PageQueryLocalModel.Tag -> PageQuery.Tag(tagKey = this.tagKey, description = this.description)
}