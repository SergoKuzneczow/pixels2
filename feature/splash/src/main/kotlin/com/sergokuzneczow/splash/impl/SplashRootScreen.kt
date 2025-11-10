package com.sergokuzneczow.splash.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import com.sergokuzneczow.splash.impl.ui.SplashScreen
import com.sergokuzneczow.splash.impl.view_model.SplashScreenViewModel
import com.sergokuzneczow.splash.impl.view_model.SplashScreenViewModelFactory

@Composable
internal fun SplashRootScreen(
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
    navigateToApplicationSetup: (NavOptions?) -> Unit,
) {
    val vm: SplashScreenViewModel = viewModel(factory = SplashScreenViewModelFactory(context = LocalContext.current))
    val uiState: SplashScreenUiState by vm.uiState.collectAsStateWithLifecycle()

    SplashScreen(
        uiState = uiState,
        onChangeProgressBar = onChangeProgressBar,
        navigateToMainMenu = navigateToMainMenu,
        navigateToApplicationSetup = navigateToApplicationSetup,
    )
}