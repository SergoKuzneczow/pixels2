package com.sergokuzneczow.suitable_pictures.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import com.sergokuzneczow.suitable_pictures.api.SuitablePicturesRoute
import com.sergokuzneczow.suitable_pictures.impl.ui.SuitablePicturesScreen
import com.sergokuzneczow.suitable_pictures.impl.view_model.SuitablePicturesScreenDependenciesViewModel
import com.sergokuzneczow.suitable_pictures.impl.view_model.SuitablePicturesScreenViewModel
import com.sergokuzneczow.utilities.lastItemBackstack
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun SuitablePicturesRootScreen(
    pageKey: Long,
    navigateToDialogPageFilterDestination: (pageKey: Long, navOption: NavOptions) -> Unit,
    navigateToSelectedPictureDestination: (pictureKey: String) -> Unit,
    backMainMenu: () -> Unit,
) {
    val dvm: SuitablePicturesScreenDependenciesViewModel = viewModel()
    val svm: SuitablePicturesScreenViewModel = viewModel(
        factory = SuitablePicturesScreenViewModel.Factory(
            pageKey = pageKey,
            dispatchersApi = dvm.dispatchersApi,
            getSuitablePicturesScreenPager4UseCase = dvm.getSuitablePicturesScreenPager4UseCase,
            getPage = dvm.getPage,
        )
    )

    svm.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SuitablePicturesScreenSideEffect.OnBackHome -> backMainMenu.invoke()
            is SuitablePicturesScreenSideEffect.OnToPageFilter -> navigateToDialogPageFilterDestination.invoke(sideEffect.pageKey, lastItemBackstack<SuitablePicturesRoute>())
            is SuitablePicturesScreenSideEffect.OnToSelectedPicture -> navigateToSelectedPictureDestination.invoke(sideEffect.pictureKey)
        }
    }

    val state: State<SuitablePicturesState> = svm.collectAsState()

    SuitablePicturesScreen(
        state = state.value,
        imageLoader = dvm.imageLoaderApi.imageLoader,
        nextPage = { svm.dispatch(SuitablePicturesScreenIntent.GetNextPage) },
        onBackMainMenu = { svm.dispatch(SuitablePicturesScreenIntent.BackHome) },
        onPictureClick = { pictureKey -> svm.dispatch(SuitablePicturesScreenIntent.ToSelectedPicture(pictureKey)) },
        onFilterButtonClick = { svm.dispatch(SuitablePicturesScreenIntent.ToPageFilter) },
    )
}