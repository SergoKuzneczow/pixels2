package com.sergokuzneczow.application_setup.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import com.sergokuzneczow.application_setup.api.ApplicationSetupScreenRoute
import com.sergokuzneczow.application_setup.impl.ui.ApplicationSetupScreen
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenDependenciesViewModel
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenViewModel
import com.sergokuzneczow.utilities.collectAction
import com.sergokuzneczow.utilities.lastItemInclusiveBackstack
import com.sergokuzneczow.utilities.state

@Composable
internal fun ApplicationSetupRootScreen(
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
) {
    val cvm: ApplicationSetupScreenDependenciesViewModel = viewModel()
    val svm: ApplicationSetupScreenViewModel = viewModel(factory = ApplicationSetupScreenViewModel.Factory(cvm.dispatchersApi, cvm.settingsRepositoryApi))

    svm.collectAction({
        when (it) {
            is ApplicationSetupScreenAction.Completed -> navigateToMainMenu.invoke(lastItemInclusiveBackstack<ApplicationSetupScreenRoute>())
        }
    })

    ApplicationSetupScreen(
        uiState = svm.state,
        onChangeThemeState = { themeState -> svm.updateIntent(ApplicationSetupScreenIntent.ChangeTheme(themeState)) },
        onChangeProgressBar = onChangeProgressBar,
        onDone = {
            svm.updateIntent(ApplicationSetupScreenIntent.Done)
        },
    )
}