package com.sergokuzneczow.application_setup.impl

import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.sergokuzneczow.application_setup.api.ApplicationSetupScreenRoute
import com.sergokuzneczow.application_setup.impl.ui.ApplicationSetupScreen
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenViewModel
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenViewModelFactory

@Composable
internal fun ApplicationSetupRootScreen(
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
) {
    val vm: ApplicationSetupScreenViewModel = viewModel(factory = ApplicationSetupScreenViewModelFactory(context = LocalContext.current))
    val uiState: ApplicationSetupScreenUiState by vm.uiState.collectAsStateWithLifecycle()

    ApplicationSetupScreen(
        uiState = uiState,
        onChangeThemeStateClick = { vm.setIntent(ApplicationSetupScreenIntent.ThemeSelected(newThemeState = it)) },
        onChangeProgressBar = onChangeProgressBar,
        onDoneClick = { vm.setIntent(ApplicationSetupScreenIntent.Done) },
        navigateToMainMenu = {
            val navOptions: NavOptions = navOptions {
                popUpTo<ApplicationSetupScreenRoute> {
                    saveState = true
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = true
            }
            navigateToMainMenu.invoke(navOptions)
        },
    )
}