package com.sergokuzneczow.application_setup.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import com.sergokuzneczow.application_setup.api.ApplicationSetupScreenRoute
import com.sergokuzneczow.application_setup.impl.ui.ApplicationSetupScreen
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenComponentViewModel
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenViewModel
import com.sergokuzneczow.base.collectAction
import com.sergokuzneczow.base.state
import com.sergokuzneczow.utilities.excludeBackstack

@Composable
internal fun ApplicationSetupRootScreen(
    onChangeProgressBar: (isVisible: Boolean) -> Unit,
    navigateToMainMenu: (NavOptions?) -> Unit,
) {
    val cvm: ApplicationSetupScreenComponentViewModel = viewModel()
    val svm: ApplicationSetupScreenViewModel = viewModel(factory = ApplicationSetupScreenViewModel.Factory(cvm.dispatchersApi, cvm.settingsRepositoryApi))

    svm.collectAction({
        when (it) {
            is ApplicationSetupScreenAction.Completed -> navigateToMainMenu.invoke(excludeBackstack<ApplicationSetupScreenRoute>())
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