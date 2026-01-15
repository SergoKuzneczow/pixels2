package com.sergokuzneczow.search_suitable_pictures.impl.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.search_suitable_pictures.impl.SearchSuitablePicturesScreenIntent
import com.sergokuzneczow.search_suitable_pictures.impl.SearchSuitablePicturesScreenSideEffect
import com.sergokuzneczow.search_suitable_pictures.impl.SearchSuitablePicturesScreenState
import com.sergokuzneczow.search_suitable_pictures.impl.SearchSuitablePicturesScreenState.SearchField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

internal class SearchSuitablePicturesScreenViewModel(
    private val pageRepositoryApi: PageRepositoryApi,
) : ViewModel(), ContainerHost<SearchSuitablePicturesScreenState, SearchSuitablePicturesScreenSideEffect> {

    override val container: Container<SearchSuitablePicturesScreenState, SearchSuitablePicturesScreenSideEffect> = (viewModelScope + Dispatchers.IO).container(SearchField())

    fun dispatch(intent: SearchSuitablePicturesScreenIntent) {
        when (intent) {
            is SearchSuitablePicturesScreenIntent.ShowSearchField -> intent {
                reduce { SearchField() }
            }

            is SearchSuitablePicturesScreenIntent.InputSearchField -> intent {
                (state as? SearchField)?.let { reduce { it.copy(searchField = intent.searchValue) } }
            }

            is SearchSuitablePicturesScreenIntent.DoneSearch -> intent {
                (state as? SearchField)?.let {
                    runCatching {
                        val pageKey: Long = getPageKey(queryWord = it.searchField)
                        postSideEffect(SearchSuitablePicturesScreenSideEffect.SearchRequest(pageKey))
                    }
                }
            }
        }
    }

    private suspend fun getPageKey(queryWord: String): Long {
        val pageQuery: PageQuery = PageQuery.KeyWord(queryWord)
        val pageFilter: PageFilter = PageFilter.DEFAULT
        val pageKey: Long? = pageRepositoryApi.getPageKey(
            pageQuery = pageQuery,
            pageFilter = pageFilter,
        )
        return pageKey ?: throw IllegalArgumentException("Property pageKey can't be null.")
    }

    internal class Factory(
        private val pageRepositoryApi: PageRepositoryApi,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(SearchSuitablePicturesScreenViewModel::class.java)) SearchSuitablePicturesScreenViewModel(pageRepositoryApi) as T
            else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}