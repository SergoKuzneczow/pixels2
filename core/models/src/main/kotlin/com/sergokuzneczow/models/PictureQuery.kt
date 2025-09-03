package com.sergokuzneczow.models


public sealed interface PictureQuery {
    public companion object {
        public val DEFAULT: Empty = Empty()
    }

    public class Empty() : PictureQuery
    public data class KeyWord(val word: String) : PictureQuery
    public data class KeyWords(val words: List<String>, val descriptions: List<String>) : PictureQuery
    public data class Like(val key: String, val description: String) : PictureQuery
    public data class Tag(val key: Int, val description: String) : PictureQuery
}