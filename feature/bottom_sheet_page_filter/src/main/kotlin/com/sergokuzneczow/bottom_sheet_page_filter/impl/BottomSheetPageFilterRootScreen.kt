package com.sergokuzneczow.bottom_sheet_page_filter.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.bottom_sheet_page_filter.impl.ui.BottomSheetPageFilterScreen

@Composable
internal fun BottomSheetPageFilterRootScreen(
    pageKey: Long,
    navigateToSuitablePicturesDestination: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    val vm: BottomSheetPageFilterViewModel = viewModel(factory = BottomSheetPageFilterViewModel.Factory(pageKey, LocalContext.current))
    val pageUiState: State<PageUiState> = vm.getPageFilterUiState().collectAsStateWithLifecycle()
    BottomSheetPageFilterScreen(
        pageUiState = pageUiState.value,
        doneNewPageFilter = { pageQuery, pageFilter ->
            vm.getPageKey(
                pageQuery = pageQuery,
                pageFilter = pageFilter,
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) }
            )
        },
        closeDialog = { popBackStack.invoke() },
    )
}