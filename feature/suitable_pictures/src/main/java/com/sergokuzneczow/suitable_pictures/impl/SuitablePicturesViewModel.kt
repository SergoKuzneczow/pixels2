package com.sergokuzneczow.suitable_pictures.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_suitable_pictures_screen_pager_use_case.GetSuitablePicturesScreenPagerUseCase
import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.suitable_pictures.impl.di.DaggerSuitablePicturesFeatureComponent
import com.sergokuzneczow.suitable_pictures.impl.di.SuitablePicturesFeatureComponent
import com.sergokuzneczow.suitable_pictures.impl.di.dependenciesProvider
import com.sergokuzneczow.suitable_pictures.impl.ui.SuitablePicturesUiState
import com.sergokuzneczow.suitable_pictures.impl.ui.TitleUiState
import com.sergokuzneczow.suitable_pictures.impl.ui.toSuitablePictures
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
    lateinit var pageRepository: PageRepositoryApi

    private val suitablePicturesFeatureComponent: SuitablePicturesFeatureComponent by lazy {
        DaggerSuitablePicturesFeatureComponent.builder()
            .setDependencies(context.dependenciesProvider.suitablePicturesFeatureDependenciesProvider())
            .build()
    }

    private val suitablePicturesUiState: MutableStateFlow<SuitablePicturesUiState> = MutableStateFlow(SuitablePicturesUiState.Default())

    private val titleUiState: MutableStateFlow<TitleUiState> = MutableStateFlow(TitleUiState.Loading())

    init {
        suitablePicturesFeatureComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            val page: Page = pageRepository.getPage(pageKey)
            log(tag = "SuitablePicturesViewModel") { "pageRepository.getPage(pageKey); page=$page" }

            titleUiState.emit(page.createScreenTitle())

            getSuitablePicturesScreenPagerUseCase.execute(
                coroutineScope = viewModelScope + Dispatchers.IO,
                pageQuery = page.query,
                pageFilter = page.filter,
                loading = {},
                completed = { isLastPage, isEmpty -> },
                error = {}
            ).onEach { answer ->
                log(tag = "SuitablePicturesViewModel") { "getSuitablePicturesScreenPagerUseCase.execute(); onEach; answer.items=${answer.items}" }
                log(tag = "SuitablePicturesViewModel") { "getSuitablePicturesScreenPagerUseCase.execute(); onEach; answer.items.size=${answer.items.size}" }
                log(tag = "SuitablePicturesViewModel") { "getSuitablePicturesScreenPagerUseCase.execute(); onEach; answer.meta=${answer.meta}" }
                suitablePicturesUiState.emit(SuitablePicturesUiState.Success(answer.items.toSuitablePictures()))
            }.launchIn(this)
        }
    }

    private fun Page.createScreenTitle(): TitleUiState {
        return when (this.query) {
            is PageQuery.Empty -> {
                if (this.filter.pictureColor.colorName.isNotEmpty()) TitleUiState.Success("Color ${this.filter.pictureColor.colorName}")
                else when (this.filter.pictureSorting) {
                    PageFilter.PictureSorting.VIEWS -> TitleUiState.Success("View")
                    PageFilter.PictureSorting.RANDOM -> TitleUiState.Success("Random")
                    PageFilter.PictureSorting.FAVORITES -> TitleUiState.Success("Favorites")
                    PageFilter.PictureSorting.TOP_LIST -> TitleUiState.Success("Top list")
                    PageFilter.PictureSorting.DATE_ADDED -> TitleUiState.Success("Date added")
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
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}