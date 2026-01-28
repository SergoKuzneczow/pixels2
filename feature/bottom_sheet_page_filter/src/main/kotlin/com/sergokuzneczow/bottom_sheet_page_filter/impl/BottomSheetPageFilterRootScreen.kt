package com.sergokuzneczow.bottom_sheet_page_filter.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.bottom_sheet_page_filter.impl.ui.BottomSheetPageFilterScreen
import com.sergokuzneczow.bottom_sheet_page_filter.impl.view_model.BottomSheetPageDependenciesViewModel
import com.sergokuzneczow.bottom_sheet_page_filter.impl.view_model.BottomSheetPageFilterViewModel
import com.sergokuzneczow.bottom_sheet_page_filter.impl.view_model.BottomSheetPageFilterViewModel.Factory
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun BottomSheetPageFilterRootScreen(
    pageKey: Long,
    navigateToSuitablePicturesDestination: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    val dvm: BottomSheetPageDependenciesViewModel = viewModel()
    val svm: BottomSheetPageFilterViewModel = viewModel(
        factory = Factory(
            pageKey = pageKey,
            dispatchersApi = dvm.dispatchersApi,
            getFirstPageKeyUseCase = dvm.getFirstPageKeyUseCase,
            getPage = dvm.getPage,
        )
    )
    svm.collectSideEffect {
        when (it) {
            is BottomSheetPageFilterScreenSideEffect.OnDone -> navigateToSuitablePicturesDestination.invoke(it.pageKey)
        }
    }
    val state: State<BottomSheetPageFilterScreenState> = svm.collectAsState()
    BottomSheetPageFilterScreen(
        state = state.value,
        onSelectSorting = { svm.dispatch(BottomSheetPageFilterScreenIntent.ChangeSelectedSorting(it)) },
        onSelectOrder = { svm.dispatch(BottomSheetPageFilterScreenIntent.ChangeSelectedOrder(it)) },
        onSelectPurities = { svm.dispatch(BottomSheetPageFilterScreenIntent.ChangeSelectedPurities(it)) },
        onSelectCategories = { svm.dispatch(BottomSheetPageFilterScreenIntent.ChangeSelectedCategories(it)) },
        onDone = { svm.dispatch(BottomSheetPageFilterScreenIntent.Done) },
        onClose = { popBackStack.invoke() },
    )
}