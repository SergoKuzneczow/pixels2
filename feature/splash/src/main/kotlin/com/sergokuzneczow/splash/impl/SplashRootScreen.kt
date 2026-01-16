package com.sergokuzneczow.splash.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import com.sergokuzneczow.splash.api.SplashScreenRoute
import com.sergokuzneczow.splash.impl.ui.SplashScreen
import com.sergokuzneczow.splash.impl.view_model.SplashScreenDependenciesViewModel
import com.sergokuzneczow.splash.impl.view_model.SplashScreenViewModel
import com.sergokuzneczow.utilities.excludeBackstack
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun SplashRootScreen(
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
    navigateToApplicationSetup: (NavOptions?) -> Unit,
) {
    onChangeProgressBar.invoke(true)

    val cvm: SplashScreenDependenciesViewModel = viewModel()
    val svm: SplashScreenViewModel = viewModel(factory = SplashScreenViewModel.Factory(dispatchersApi = cvm.dispatchersApi, cvm.settingsRepositoryApi))

    svm.collectSideEffect { action ->
        when (action) {
            is SplashScreenAction.IsFirstLaunch -> navigateToApplicationSetup.invoke(excludeBackstack<SplashScreenRoute>())
            is SplashScreenAction.IsNotFirstLaunch -> navigateToMainMenu.invoke(excludeBackstack<SplashScreenRoute>())
        }
    }

    val state: State<SplashScreenState> = svm.collectAsState()

    SplashScreen(state = state.value)
}