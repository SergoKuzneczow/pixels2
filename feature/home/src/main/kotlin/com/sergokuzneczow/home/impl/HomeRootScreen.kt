package com.sergokuzneczow.home.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.home.impl.ui.HomeScreen

@Composable
internal fun HomeScreenRoot(
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    titleTextState.value = stringResource(com.sergokuzneczow.home.R.string.feature_home_title)
    val vm: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory(LocalContext.current))
    progressBarIsVisible.value = vm.getProgressBarUiState().collectAsStateWithLifecycle().value
    val homeListUiState: HomeListUiState by vm.getHomeListUiState().collectAsStateWithLifecycle()
    HomeScreen(
        homeListUiState = homeListUiState,
        itemClick = { pageQuery, pageFilter ->
            vm.getPageKey(
                pageQuery = pageQuery, pageFilter = pageFilter,
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
        },
        nextPage = vm::nextPage,
    )
}