package com.sergokuzneczow.home.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.home.R
import com.sergokuzneczow.home.impl.ui.HomeScreen
import com.sergokuzneczow.home.impl.view_model.HomeScreenDependenciesViewModel
import com.sergokuzneczow.home.impl.view_model.HomeScreenViewModel
import com.sergokuzneczow.home.impl.view_model.HomeScreenViewModel.Factory
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun HomeScreenRoot(
    onShowSnackbar: suspend (message: String, actionOrNull: String?) -> Unit,
    titleTextState: MutableState<String>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    titleTextState.value = stringResource(R.string.feature_home_title)

    val dvm: HomeScreenDependenciesViewModel = viewModel()

    val svm: HomeScreenViewModel = viewModel(
        factory = Factory(
            dispatchersApi = dvm.dispatchersApi,
            getHomeScreenPager4UseCase = dvm.getHomeScreenPager4UseCase,
            getFirstPageKeyUseCase = dvm.getFirstPageKeyUseCase,
        )
    )

    svm.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeScreenSideEffect.ShowPages -> navigateToSuitablePicturesDestination.invoke(sideEffect.pageKey)
        }
    }

    HomeScreen(
        state = svm.collectAsState().value,
        imageLoader = dvm.imageLoaderApi.imageLoader,
        onSelectPage = { pageQuery, pageFilter -> svm.dispatch(HomeScreenIntent.SelectQuery(pageQuery, pageFilter)) },
        onNextPage = { svm.dispatch(HomeScreenIntent.NextPage) },
    )
}