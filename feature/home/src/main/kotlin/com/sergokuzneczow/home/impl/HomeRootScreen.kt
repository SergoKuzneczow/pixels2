package com.sergokuzneczow.home.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.home.impl.ui.HomeScreen
import com.sergokuzneczow.home.impl.ui.SuggestedQueriesUiState

@Composable
internal fun HomeScreenRoot(
    titleState: MutableState<String>,
    showProgressBar: (visible: Boolean) -> Unit,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    titleState.value = stringResource(com.sergokuzneczow.home.R.string.feature_home_title)
    val vm: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory(LocalContext.current, showProgressBar))
    val suggestedQueriesUiState: SuggestedQueriesUiState by vm.getSuggestQueriesUiState().collectAsStateWithLifecycle()
    HomeScreen(
        suggestedQueriesUiState = suggestedQueriesUiState,
        suggestedQueryClick = { pageQuery, pageFilter ->
            vm.getPageKey(
                pageQuery = pageQuery, pageFilter = pageFilter,
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
        },
        nextPage = vm::nextPage,
    )
}