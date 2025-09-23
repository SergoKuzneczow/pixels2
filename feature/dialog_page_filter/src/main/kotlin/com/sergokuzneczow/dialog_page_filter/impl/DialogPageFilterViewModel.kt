package com.sergokuzneczow.dialog_page_filter.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.dialog_page_filter.impl.di.DaggerDialogPageFilterComponent
import com.sergokuzneczow.dialog_page_filter.impl.di.DialogPageFilterComponent
import com.sergokuzneczow.dialog_page_filter.impl.di.dependenciesProvider
import com.sergokuzneczow.models.Page
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.utilities.logger.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DialogPageFilterViewModel(
    private val pageKey: Long,
    @NonUiContext context: Context,
) : ViewModel() {

    @Inject
    lateinit var pageRepository: PageRepositoryApi

    private val dialogPageFilterComponent: DialogPageFilterComponent by lazy {
        DaggerDialogPageFilterComponent.builder()
            .setDependencies(context.dependenciesProvider.dialogPageFilterDependenciesProvider())
            .build()
    }

    private val pageUiState: MutableStateFlow<PageUiState> = MutableStateFlow(PageUiState.Loading)

    init {
        dialogPageFilterComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            val page: Page = pageRepository.getPage(pageKey)
            log(tag = "DialogPageFilterViewModel") { "pageRepository.getPage(); page=$page" }
            pageUiState.emit(PageUiState.Success(pageQuery = page.query, pageFilter = page.filter))
        }
    }

    internal fun getPageFilterUiState(): StateFlow<PageUiState> = pageUiState.asStateFlow()

    internal fun getPageKey(pageQuery: PageQuery, pageFilter: PageFilter, completed: (pageKey: Long) -> Unit) {
        viewModelScope.launch {
            val pageKey: Long? = pageRepository.getPageKey(
                pageQuery = pageQuery,
                pageFilter = pageFilter,
            )
            pageKey?.let { pageKey -> completed.invoke(pageKey) }
        }
    }

    internal class Factory(
        private val pageKey: Long,
        @NonUiContext private val context: Context,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DialogPageFilterViewModel::class.java)) {
                return DialogPageFilterViewModel(
                    pageKey = pageKey,
                    context = context,
                ) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}