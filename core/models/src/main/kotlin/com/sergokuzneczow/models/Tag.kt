package com.sergokuzneczow.models

public data class Tag(
    public val id: Int,
    public val name: String,
    public val alias: String,
    public val categoryId: Int,
    public val categoryName: String,
    public val purity: TagPurity,
    public val createdAt: String,
) {

//    public enum class TagCategory {
//        GENERAL,
//        ANIME,
//        PEOPLE,
//    }

    public enum class TagPurity {
        SFW,
        SKETCHY,
        NSFW,
    }
}