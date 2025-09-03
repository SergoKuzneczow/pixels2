package com.sergokuzneczow.models

public data class PictureFilter(
    val pictureSorting: PictureSorting,
    val pictureOrder: PictureOrder,
    val picturePurities: PicturePurities,
    val pictureCategories: PictureCategories,
    val pictureColor: PictureColor,
) {
    public companion object {
        public val DEFAULT: PictureFilter = PictureFilter(
            pictureSorting = PictureSorting.DATE_ADDED,
            pictureOrder = PictureOrder.DESC,
            picturePurities = PicturePurities(
                sfw = true,
                sketchy = false,
                nsfw = false,
            ),
            pictureCategories = PictureCategories(
                general = true,
                anime = true,
                people = true
            ),
            pictureColor = PictureColor.ANY
        )
    }

    public enum class PictureSorting {
        VIEWS,
        RANDOM,
        FAVORITES,
        TOP_LIST,
        DATE_ADDED;
    }

    public enum class PictureOrder {
        DESC,
        ASC;
    }

    public data class PicturePurities(
        val sfw: Boolean,
        val sketchy: Boolean,
        val nsfw: Boolean,
    )

    public data class PictureCategories(
        val general: Boolean,
        val anime: Boolean,
        val people: Boolean,
    )

    public data class PictureColor(public val value: String) {
        public companion object {
            public val ANY: PictureColor = PictureColor(value = "")
        }
    }
}