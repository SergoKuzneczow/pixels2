package com.sergokuzneczow.home.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.home.R
import com.sergokuzneczow.home.impl.ui.HomeScreen
import com.sergokuzneczow.home.impl.view_model.HomeScreenViewModel
import com.sergokuzneczow.home.impl.view_model.HomeScreenViewModelFactory

@Composable
internal fun HomeScreenRoot(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    titleTextState: MutableState<String>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    val vm: HomeScreenViewModel = viewModel(factory = HomeScreenViewModelFactory(LocalContext.current))
    titleTextState.value = stringResource(R.string.feature_home_title)
    val homeListUiState: HomeListUiState by vm.getHomeListUiState().collectAsStateWithLifecycle()
    val exceptionsUiState: String? by vm.getExceptionsFlow().collectAsStateWithLifecycle()
    HomeScreen(
        homeListUiState = homeListUiState,
        itemClick = { pageQuery, pageFilter ->
            vm.getPageKey(
                pageQuery = pageQuery, pageFilter = pageFilter,
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
        },
        nextPage = vm::nextPage,
    )
    LaunchedEffect(exceptionsUiState) {
        exceptionsUiState?.let { onShowSnackbar.invoke(it, null) }
    }
}