package com.sergokuzneczow.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.home.ui.HomeScreen
import com.sergokuzneczow.home.ui.SuggestedQueriesUiState
import com.sergokuzneczow.utilities.logger.log
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
internal fun HomeScreenRoot(
    changeTitle: (title: String) -> Unit,
    showProgressBar: (visible: Boolean) -> Unit,
) {
    changeTitle.invoke(stringResource(R.string.feature_home_title))
    val vm: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory(LocalContext.current, showProgressBar))
    val suggestedQueriesUiState: SuggestedQueriesUiState by vm.getSuggestQueriesUiState().collectAsStateWithLifecycle()
    HomeScreen(
        suggestedQueriesUiState = suggestedQueriesUiState,
        suggestedQueryClick = {  },
        nextPage = vm::nextPage,
    )
}