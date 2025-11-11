package com.sergokuzneczow.models


public sealed interface PageQuery {
    public companion object {
        public val DEFAULT: Empty = Empty
    }

    public data object Empty : PageQuery
    public data class KeyWord(val word: String) : PageQuery
    public data class KeyWords(val words: List<String>, val descriptions: List<String>) : PageQuery
    public data class Like(val pictureKey: String, val description: String) : PageQuery
    public data class Tag(val tagKey: Int, val description: String) : PageQuery
}