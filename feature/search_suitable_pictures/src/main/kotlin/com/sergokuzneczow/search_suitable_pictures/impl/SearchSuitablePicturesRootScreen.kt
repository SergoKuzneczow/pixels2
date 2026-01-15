package com.sergokuzneczow.search_suitable_pictures.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.search_suitable_pictures.R
import com.sergokuzneczow.search_suitable_pictures.impl.ui.SearchSuitablePicturesScreen
import com.sergokuzneczow.search_suitable_pictures.impl.view_models.SearchSuitablePicturesScreenDependenciesViewModel
import com.sergokuzneczow.search_suitable_pictures.impl.view_models.SearchSuitablePicturesScreenViewModel
import com.sergokuzneczow.search_suitable_pictures.impl.view_models.SearchSuitablePicturesScreenViewModel.Factory
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun SearchSuitablePicturesRootScreen(
    titleTextState: MutableState<String>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    titleTextState.value = stringResource(R.string.feature_search_suitable_pictures_title)

    val dvm: SearchSuitablePicturesScreenDependenciesViewModel = viewModel()
    val svm: SearchSuitablePicturesScreenViewModel = viewModel(factory = Factory(dvm.pageRepositoryApi))

    svm.collectSideEffect {
        when (it) {
            is SearchSuitablePicturesScreenSideEffect.SearchRequest -> {
                navigateToSuitablePicturesDestination.invoke(it.pageKey)
            }
        }
    }

    val state: State<SearchSuitablePicturesScreenState> = svm.container.stateFlow.collectAsStateWithLifecycle()

    SearchSuitablePicturesScreen(
        state = state.value,
        onSearchFieldChange = { inputValue -> svm.dispatch(SearchSuitablePicturesScreenIntent.InputSearchField(inputValue)) },
        onSearchDone = { svm.dispatch(SearchSuitablePicturesScreenIntent.DoneSearch) },
    )
}