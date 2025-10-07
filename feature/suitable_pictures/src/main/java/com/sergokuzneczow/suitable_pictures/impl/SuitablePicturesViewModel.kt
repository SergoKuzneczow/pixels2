package com.sergokuzneczow.suitable_pictures.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPager4UseCase
import com.sergokuzneczow.domain.pager4.IPixelsPager4
import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.suitable_pictures.impl.di.DaggerSuitablePicturesFeatureComponent
import com.sergokuzneczow.suitable_pictures.impl.di.SuitablePicturesFeatureComponent
import com.sergokuzneczow.suitable_pictures.impl.di.dependenciesProvider
import com.sergokuzneczow.utilities.logger.Level
import com.sergokuzneczow.utilities.logger.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

internal class SuitablePicturesViewModel(
    private val pageKey: Long,
    @NonUiContext context: Context,
) : ViewModel() {

    @Inject
    lateinit var getSuitablePicturesScreenPager4UseCase: GetSuitablePicturesScreenPager4UseCase

    @Inject
    lateinit var getPage: GetPage

    private val suitablePicturesFeatureComponent: SuitablePicturesFeatureComponent by lazy {
        DaggerSuitablePicturesFeatureComponent.builder()
            .setDependencies(context.dependenciesProvider.suitablePicturesFeatureDependenciesProvider())
            .build()
    }

    private val suitablePicturesUiState: MutableStateFlow<SuitablePicturesUiState> = MutableStateFlow(SuitablePicturesUiState.Loading)

    private val titleUiState: MutableStateFlow<TitleUiState> = MutableStateFlow(TitleUiState.Loading())

    private val exceptionsFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        suitablePicturesFeatureComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            val page: Page = getPage.execute(pageKey)

            titleUiState.emit(page.createScreenTitle())

            getSuitablePicturesScreenPager4UseCase.execute(
                coroutineScope = viewModelScope + Dispatchers.IO,
                pageQuery = page.query,
                pageFilter = page.filter,
            ).onEach { pages ->
                val suitablePicturesPages: List<SuitablePicturesPage> = pages.pages.toSuitablePicturesPages()

                if (pages.pages.values.lastOrNull()?.pageState is IPixelsPager4.Answer.Page.PageState.Error) {
                    exceptionsFlow.emit((pages.pages.values.lastOrNull()?.pageState as IPixelsPager4.Answer.Page.PageState.Error).message)
                } else exceptionsFlow.emit(null)

                when {
                    pages.meta.empty -> suitablePicturesUiState.emit(SuitablePicturesUiState.Empty)
                    suitablePicturesPages.firstOrNull(predicate = { it.items.isNotEmpty() }) != null ->
                        suitablePicturesUiState.emit(SuitablePicturesUiState.Success(suitablePicturesPages))
                }

            }.launchIn(this)
        }
    }

    fun getTitleUiState(): StateFlow<TitleUiState> = titleUiState.asStateFlow()
    fun getSuitablePicturesUiState(): StateFlow<SuitablePicturesUiState> = suitablePicturesUiState.asStateFlow()

    fun getExceptionsFlow(): MutableStateFlow<String?> = exceptionsFlow

    fun nextPage() {
        log(tag = "SuitablePicturesViewModel", level = Level.INFO) { "nextPage()" }
        getSuitablePicturesScreenPager4UseCase.nextPage()
    }

    private fun Page.createScreenTitle(): TitleUiState {
        return when (this.query) {
            is PageQuery.Empty -> {
                when {
                    this.filter.pictureColor.colorName.isNotEmpty() -> TitleUiState.Success("Color ${this.filter.pictureColor.colorName}")
                    this.filter.pictureOrder == PageFilter.PictureOrder.DESC -> {
                        when (this.filter.pictureSorting) {
                            PageFilter.PictureSorting.VIEWS -> TitleUiState.Success("View")
                            PageFilter.PictureSorting.RANDOM -> TitleUiState.Success("Random")
                            PageFilter.PictureSorting.FAVORITES -> TitleUiState.Success("Loved")
                            PageFilter.PictureSorting.TOP_LIST -> TitleUiState.Success("Bests")
                            PageFilter.PictureSorting.DATE_ADDED -> TitleUiState.Success("New")
                        }
                    }

                    this.filter.pictureOrder == PageFilter.PictureOrder.ASC -> {
                        when (this.filter.pictureSorting) {
                            PageFilter.PictureSorting.VIEWS -> TitleUiState.Success("Invisible")
                            PageFilter.PictureSorting.RANDOM -> TitleUiState.Success("Random")
                            PageFilter.PictureSorting.FAVORITES -> TitleUiState.Success("Unloved")
                            PageFilter.PictureSorting.TOP_LIST -> TitleUiState.Success("Worst")
                            PageFilter.PictureSorting.DATE_ADDED -> TitleUiState.Success("Old")
                        }
                    }

                    else -> TitleUiState.Success("Default")
                }
            }

            is PageQuery.KeyWord -> {
                TitleUiState.Success(title = (this.query as PageQuery.KeyWord).word.replaceFirstChar { it.uppercase() })
            }

            is PageQuery.KeyWords -> {
                val itemsAsString: String = (this.query as PageQuery.KeyWords).descriptions.joinToString(", ").replaceFirstChar { it.uppercase() }
                TitleUiState.Success(title = if (itemsAsString.length > 35) itemsAsString.substring(0, 35) else itemsAsString)
            }

            is PageQuery.Like -> {
                val itemsAsString: String = (this.query as PageQuery.Like).description.replaceFirstChar { it.uppercase() }
                TitleUiState.Success(title = if (itemsAsString.length > 35) itemsAsString.substring(0, 35) else itemsAsString)
            }

            is PageQuery.Tag -> {
                val itemsAsString: String = (this.query as PageQuery.Tag).description.replaceFirstChar { it.uppercase() }
                TitleUiState.Success(title = "#${if (itemsAsString.length > 35) itemsAsString.substring(0, 35) else itemsAsString}")
            }
        }
    }

    internal class Factory(
        private val pageKey: Long,
        @NonUiContext private val context: Context,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SuitablePicturesViewModel::class.java)) {
                return SuitablePicturesViewModel(
                    pageKey = pageKey,
                    context = context,
                ) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}