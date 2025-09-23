package com.sergokuzneczow.dialog_page_filter.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.dialog_page_filter.impl.ui.DialogPageFilterScreen

@Composable
internal fun DialogPageFilterRootScreen(
    pageKey: Long,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    val vm: DialogPageFilterViewModel = viewModel(factory = DialogPageFilterViewModel.Factory(pageKey, LocalContext.current))
    val pageUiState: State<PageUiState> = vm.getPageFilterUiState().collectAsStateWithLifecycle()
    DialogPageFilterScreen(
        pageUiState = pageUiState.value,
        doneNewPageFilter = { pageQuery, pageFilter ->
            vm.getPageKey(
                pageQuery = pageQuery,
                pageFilter = pageFilter,
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) }
            )
        }
    )
}