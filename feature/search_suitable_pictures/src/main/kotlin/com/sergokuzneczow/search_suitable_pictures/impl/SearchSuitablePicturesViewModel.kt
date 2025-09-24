package com.sergokuzneczow.search_suitable_pictures.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.search_suitable_pictures.impl.di.DaggerSearchSuitablePicturesComponent
import com.sergokuzneczow.search_suitable_pictures.impl.di.SearchSuitablePicturesComponent
import com.sergokuzneczow.search_suitable_pictures.impl.di.dependenciesProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SearchSuitablePicturesViewModel(
    @NonUiContext context: Context
) : ViewModel() {

    @Inject
    lateinit var pageRepository: PageRepositoryApi

    private val searchSuitablePiComponent: SearchSuitablePicturesComponent = DaggerSearchSuitablePicturesComponent.builder()
        .setDep(context.dependenciesProvider.searchSuitablePicturesDependenciesProvider())
        .build()

    init {
        searchSuitablePiComponent.inject(this)
    }

    fun getPageKey(queryWord: String, completed: (pageKey: Long) -> Unit) {
        viewModelScope.launch {
            val pageQuery = PageQuery.KeyWord(queryWord)
            val pageFilter = PageFilter.DEFAULT
            val pageKey: Long? = pageRepository.getPageKey(
                pageQuery = pageQuery,
                pageFilter = pageFilter,
            )
            pageKey?.let { pageKey -> completed.invoke(pageKey) }
        }
    }

    internal class Factory(@NonUiContext private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchSuitablePicturesViewModel::class.java)) {
                return SearchSuitablePicturesViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}