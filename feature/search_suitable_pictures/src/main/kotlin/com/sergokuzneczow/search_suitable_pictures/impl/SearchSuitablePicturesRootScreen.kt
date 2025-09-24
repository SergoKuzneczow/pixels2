package com.sergokuzneczow.search_suitable_pictures.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.search_suitable_pictures.R
import com.sergokuzneczow.search_suitable_pictures.impl.ui.SearchSuitablePicturesScreen

@Composable
internal fun SearchSuitablePicturesRootScreen(
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
    navigateToSuitablePicturesDestination: (pageKey: Long) -> Unit,
) {
    titleTextState.value = stringResource(R.string.feature_search_suitable_pictures_title)
    val vm: SearchSuitablePicturesViewModel = viewModel(factory = SearchSuitablePicturesViewModel.Factory(LocalContext.current))

    SearchSuitablePicturesScreen(
        searchDone = { queryWord ->
            vm.getPageKey(
                queryWord = queryWord,
                completed = { pageKey -> navigateToSuitablePicturesDestination.invoke(pageKey) })
        }
    )
}