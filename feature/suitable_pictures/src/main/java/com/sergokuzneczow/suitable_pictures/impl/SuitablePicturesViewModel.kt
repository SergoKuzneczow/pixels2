package com.sergokuzneczow.suitable_pictures.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPagerUseCase
import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.suitable_pictures.impl.di.DaggerSuitablePicturesFeatureComponent
import com.sergokuzneczow.suitable_pictures.impl.di.SuitablePicturesFeatureComponent
import com.sergokuzneczow.suitable_pictures.impl.di.dependenciesProvider
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
    lateinit var getSuitablePicturesScreenPagerUseCase: GetSuitablePicturesScreenPagerUseCase

    @Inject
    lateinit var getPage: GetPage

    private val suitablePicturesFeatureComponent: SuitablePicturesFeatureComponent by lazy {
        DaggerSuitablePicturesFeatureComponent.builder()
            .setDependencies(context.dependenciesProvider.suitablePicturesFeatureDependenciesProvider())
            .build()
    }

    private val suitablePicturesUiState: MutableStateFlow<SuitablePicturesUiState> = MutableStateFlow(SuitablePicturesUiState.Loading)

    private val titleUiState: MutableStateFlow<TitleUiState> = MutableStateFlow(TitleUiState.Loading())

    init {
        log(tag = "SuitablePicturesViewModel") { "init()" }
        suitablePicturesFeatureComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            val page: Page = getPage.execute(pageKey)
            log(tag = "SuitablePicturesViewModel") { "pageRepository.getPage(pageKey); page=$page" }

            titleUiState.emit(page.createScreenTitle())

            getSuitablePicturesScreenPagerUseCase.execute(
                coroutineScope = viewModelScope + Dispatchers.IO,
                pageQuery = page.query,
                pageFilter = page.filter,
                loading = {},
                completed = { isLastPage, isEmpty -> },
                error = {}
            ).onEach { pages ->
                pages.pages.forEach { (key, value) ->
                    log(tag = "SuitablePicturesViewModel") { "getSuitablePicturesScreenPagerUseCase.execute().onEach().it=(key=$key, value=$value)" }
                }
                suitablePicturesUiState.emit(SuitablePicturesUiState.Success(pages.pages.toSuitablePicturesPages()))
            }.launchIn(this)
        }
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

    fun getTitleUiState(): StateFlow<TitleUiState> = titleUiState.asStateFlow()
    fun getSuitablePicturesUiState(): StateFlow<SuitablePicturesUiState> = suitablePicturesUiState.asStateFlow()

    fun nextPage() {
        getSuitablePicturesScreenPagerUseCase.nextPage()
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