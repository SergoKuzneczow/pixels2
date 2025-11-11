package com.sergokuzneczow.home.impl

import androidx.compose.runtime.Composable
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
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    titleTextState: MutableState<String>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    titleTextState.value = stringResource(R.string.feature_home_title)

    val vm: HomeScreenViewModel = viewModel(factory = HomeScreenViewModelFactory(LocalContext.current))
    val homeListUiState: HomeListUiState by vm.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = homeListUiState,
        onChangeProgressBar = onChangeProgressBar,
        onSelectQuery = { pageQuery, pageFilter ->
            vm.setIntent(
                HomeListIntent.SelectQuery(
                    pageQuery = pageQuery,
                    pageFilter = pageFilter,
                    completed = { navigateToSuitablePicturesDestination.invoke(it) }
                )
            )
        },
        nextPage = { vm.setIntent(HomeListIntent.NextPage) },
    )
}