package com.sergokuzneczow.bottom_sheet_page_filter.impl.model

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageFilter.PictureSorting.DATE_ADDED
import com.sergokuzneczow.models.PageFilter.PictureSorting.FAVORITES
import com.sergokuzneczow.models.PageFilter.PictureSorting.RANDOM
import com.sergokuzneczow.models.PageFilter.PictureSorting.TOP_LIST
import com.sergokuzneczow.models.PageFilter.PictureSorting.VIEWS

internal data class PageFilterItem<T>(
    val value: T,
    val title: String,
    val isSelected: Boolean,
)

internal val PageFilter.PictureSorting.asPageFilterItemPictureSorting: List<PageFilterItem<PageFilter.PictureSorting>>
    get() = buildList {
        PageFilter.PictureSorting.entries.forEach { sorting ->
            when (sorting) {
                VIEWS -> {
                    add(
                        PageFilterItem(
                            value = sorting,
                            title = "Views",
                            isSelected = this@asPageFilterItemPictureSorting == VIEWS
                        )
                    )
                }

                FAVORITES -> {
                    add(
                        PageFilterItem(
                            value = sorting,
                            title = "Loved",
                            isSelected = this@asPageFilterItemPictureSorting == FAVORITES
                        )
                    )
                }

                TOP_LIST -> {
                    add(
                        PageFilterItem(
                            value = sorting,
                            title = "Bests",
                            isSelected = this@asPageFilterItemPictureSorting == TOP_LIST
                        )
                    )
                }

                DATE_ADDED -> {
                    add(
                        PageFilterItem(
                            value = sorting,
                            title = "Date",
                            isSelected = this@asPageFilterItemPictureSorting == DATE_ADDED
                        )
                    )
                }

                RANDOM -> {}
            }
        }
    }

internal val List<PageFilterItem<PageFilter.PictureSorting>>.asPageFilterPictureSorting: PageFilter.PictureSorting
    get() = this.firstOrNull(predicate = { it.isSelected })?.value ?: throw IllegalStateException("List<PageFilterItem<PageFilter.PictureSorting>> must contain one selected item.")

internal val PageFilter.PictureOrder.asPageFilterItemPictureOrder: List<PageFilterItem<PageFilter.PictureOrder>>
    get() = buildList {
        PageFilter.PictureOrder.entries.forEach { order ->
            when (order) {
                PageFilter.PictureOrder.DESC -> {
                    add(
                        PageFilterItem(
                            value = order,
                            title = "Descending",
                            isSelected = this@asPageFilterItemPictureOrder == order
                        )
                    )
                }

                PageFilter.PictureOrder.ASC -> {
                    add(

                        PageFilterItem(
                            value = order,
                            title = "Ascending",
                            isSelected = this@asPageFilterItemPictureOrder == order
                        )
                    )
                }
            }
        }
    }

internal val List<PageFilterItem<PageFilter.PictureOrder>>.asPageFilterPictureOrder: PageFilter.PictureOrder
    get() = this.firstOrNull(predicate = { it.isSelected })?.value ?: throw IllegalStateException("List<PageFilterItem<PageFilter.PictureSorting>> must contain one selected item.")

internal val PageFilter.PicturePurities.asPageFilterItemPicturePurities: List<PageFilterItem<PageFilter.PicturePurities>>
    get() = buildList {
        add(
            PageFilterItem(
                value = this@asPageFilterItemPicturePurities,
                title = "Sfw",
                isSelected = this@asPageFilterItemPicturePurities.sfw,
            )
        )
        add(
            PageFilterItem(
                value = this@asPageFilterItemPicturePurities,
                title = "Sketchy",
                isSelected = this@asPageFilterItemPicturePurities.sketchy,
            )
        )
        add(
            PageFilterItem(
                value = this@asPageFilterItemPicturePurities,
                title = "Nsfw",
                isSelected = this@asPageFilterItemPicturePurities.nsfw,
            )
        )
    }


internal val List<PageFilterItem<PageFilter.PicturePurities>>.asPageFilterPicturePurities: PageFilter.PicturePurities
    get() = PageFilter.PicturePurities(
        sfw = this[0].isSelected,
        sketchy = this[1].isSelected,
        nsfw = this[2].isSelected,
    )

internal val PageFilter.PictureCategories.asPageFilterItemPictureCategories: List<PageFilterItem<PageFilter.PictureCategories>>
    get() = buildList {
        add(
            PageFilterItem(
                value = this@asPageFilterItemPictureCategories,
                title = "General",
                isSelected = this@asPageFilterItemPictureCategories.general,
            )
        )
        add(
            PageFilterItem(
                value = this@asPageFilterItemPictureCategories,
                title = "Anime",
                isSelected = this@asPageFilterItemPictureCategories.anime,
            )
        )
        add(
            PageFilterItem(
                value = this@asPageFilterItemPictureCategories,
                title = "People",
                isSelected = this@asPageFilterItemPictureCategories.people,
            )
        )
    }


internal val List<PageFilterItem<PageFilter.PictureCategories>>.asPageFilterPictureCategories: PageFilter.PictureCategories
    get() = PageFilter.PictureCategories(
        general = this[0].isSelected,
        anime = this[1].isSelected,
        people = this[2].isSelected,
    )