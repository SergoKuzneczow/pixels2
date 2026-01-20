package com.sergokuzneczow.suitable_pictures.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPager4UseCase
import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesScreenIntent
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesScreenSideEffect
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesState
import com.sergokuzneczow.suitable_pictures.impl.model.SuitablePicturesPage
import com.sergokuzneczow.suitable_pictures.impl.model.hasPages
import com.sergokuzneczow.suitable_pictures.impl.model.toSuitablePicturesPages
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container

@OptIn(OrbitExperimental::class)
internal class SuitablePicturesScreenViewModel(
    private val pageKey: Long,
    dispatchersApi: DispatchersApi,
    private val getSuitablePicturesScreenPager4UseCase: GetSuitablePicturesScreenPager4UseCase,
    private val getPage: GetPage,
) : ViewModel(), ContainerHost<SuitablePicturesState, SuitablePicturesScreenSideEffect> {

    override val container: Container<SuitablePicturesState, SuitablePicturesScreenSideEffect> =
        (viewModelScope + dispatchersApi.default).container(SuitablePicturesState.Loading())

    init {
        viewModelScope.launch(dispatchersApi.io) {
            val page: Page = getPage.execute(pageKey)

            intent { reduce { SuitablePicturesState.Loading(title = page.getScreenTitle()) } }

            getSuitablePicturesScreenPager4UseCase.execute(
                coroutineScope = viewModelScope + Dispatchers.IO,
                pageQuery = page.query,
                pageFilter = page.filter,
            ).onEach { pages ->

                when {
                    pages.pages.values.lastOrNull()?.pageState is IPixelsPager4.Answer.Page.PageState.Error -> {
                        // здесь должно быть изменение состояния в ситуации, когда приходит ошибка загрузки последней страницы
                    }

                    pages.meta.empty -> intent {
                        reduce { SuitablePicturesState.Empty(state.title) }
                    }

                    pages.meta.empty.not() -> intent {
                        val suitablePicturesPages: List<SuitablePicturesPage> = pages.pages.toSuitablePicturesPages()
                        if (suitablePicturesPages.hasPages()) reduce { SuitablePicturesState.Success(state.title, suitablePicturesPages) }
                    }
                }
            }.launchIn(this)
        }
    }


    fun dispatch(intent: SuitablePicturesScreenIntent) {
        when (intent) {
            SuitablePicturesScreenIntent.BackHome -> intent {
                postSideEffect(SuitablePicturesScreenSideEffect.OnBackHome)
            }

            SuitablePicturesScreenIntent.ToPageFilter -> intent {
                postSideEffect(SuitablePicturesScreenSideEffect.OnToPageFilter(pageKey))
            }

            is SuitablePicturesScreenIntent.ToSelectedPicture -> intent {
                postSideEffect(SuitablePicturesScreenSideEffect.OnToSelectedPicture(intent.pictureKey))
            }

            SuitablePicturesScreenIntent.GetNextPage -> intent {
                getSuitablePicturesScreenPager4UseCase.nextPage()
            }
        }
    }

    private fun Page.getScreenTitle(): String {
        return when (this.query) {
            is PageQuery.Empty -> {
                when {
                    this.filter.pictureColor.colorName.isNotEmpty() -> "Color ${this.filter.pictureColor.colorName}"
                    this.filter.pictureOrder == PageFilter.PictureOrder.DESC -> {
                        when (this.filter.pictureSorting) {
                            PageFilter.PictureSorting.VIEWS -> "View"
                            PageFilter.PictureSorting.RANDOM -> "Random"
                            PageFilter.PictureSorting.FAVORITES -> "Loved"
                            PageFilter.PictureSorting.TOP_LIST -> "Bests"
                            PageFilter.PictureSorting.DATE_ADDED -> "New"
                        }
                    }

                    this.filter.pictureOrder == PageFilter.PictureOrder.ASC -> {
                        when (this.filter.pictureSorting) {
                            PageFilter.PictureSorting.VIEWS -> "Invisible"
                            PageFilter.PictureSorting.RANDOM -> "Random"
                            PageFilter.PictureSorting.FAVORITES -> "Unloved"
                            PageFilter.PictureSorting.TOP_LIST -> "Worst"
                            PageFilter.PictureSorting.DATE_ADDED -> "Old"
                        }
                    }

                    else -> "Default"
                }
            }

            is PageQuery.KeyWord -> {
                (this.query as PageQuery.KeyWord).word.replaceFirstChar { it.uppercase() }
            }

            is PageQuery.KeyWords -> {
                val itemsAsString: String = (this.query as PageQuery.KeyWords).descriptions.joinToString(", ").replaceFirstChar { it.uppercase() }
                if (itemsAsString.length > 35) itemsAsString.take(35) else itemsAsString
            }

            is PageQuery.Like -> {
                val itemsAsString: String = (this.query as PageQuery.Like).description.replaceFirstChar { it.uppercase() }
                if (itemsAsString.length > 35) itemsAsString.take(35) else itemsAsString
            }

            is PageQuery.Tag -> {
                val itemsAsString: String = (this.query as PageQuery.Tag).description.replaceFirstChar { it.uppercase() }
                "#${if (itemsAsString.length > 35) itemsAsString.take(35) else itemsAsString}"
            }
        }
    }

    internal class Factory(
        private val pageKey: Long,
        private val dispatchersApi: DispatchersApi,
        private val getSuitablePicturesScreenPager4UseCase: GetSuitablePicturesScreenPager4UseCase,
        private val getPage: GetPage,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(SuitablePicturesScreenViewModel::class.java)) {
                SuitablePicturesScreenViewModel(
                    pageKey = pageKey,
                    dispatchersApi = dispatchersApi,
                    getSuitablePicturesScreenPager4UseCase = getSuitablePicturesScreenPager4UseCase,
                    getPage = getPage,
                ) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}