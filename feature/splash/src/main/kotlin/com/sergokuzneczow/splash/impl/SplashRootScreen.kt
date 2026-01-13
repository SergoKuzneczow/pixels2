package com.sergokuzneczow.splash.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import com.sergokuzneczow.base.collectAction
import com.sergokuzneczow.base.state
import com.sergokuzneczow.splash.api.SplashScreenRoute
import com.sergokuzneczow.splash.impl.ui.SplashScreen
import com.sergokuzneczow.splash.impl.view_model.SplashScreenComponentViewModel
import com.sergokuzneczow.splash.impl.view_model.SplashScreenViewModel
import com.sergokuzneczow.utilities.excludeBackstack

@Composable
internal fun SplashRootScreen(
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
    navigateToApplicationSetup: (NavOptions?) -> Unit,
) {
    val cvm: SplashScreenComponentViewModel = viewModel()
    val svm: SplashScreenViewModel = viewModel(factory = SplashScreenViewModel.Factory(dispatchersApi = cvm.dispatchersApi, cvm.settingsRepositoryApi))

    svm.collectAction({ action ->
        when (action) {
            is SplashScreenAction.IsFirstLaunch -> navigateToApplicationSetup.invoke(excludeBackstack<SplashScreenRoute>())
            is SplashScreenAction.IsNotFirstLaunch -> navigateToMainMenu.invoke(excludeBackstack<SplashScreenRoute>())
        }
    })

    SplashScreen(
        state = svm.state,
        onChangeProgressBar = onChangeProgressBar,
    )
}