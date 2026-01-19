package com.sergokuzneczow.settings.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.settings.R
import com.sergokuzneczow.settings.impl.ui.SettingsScreen
import com.sergokuzneczow.settings.impl.view_model.SettingsScreenDependenciesViewModel
import com.sergokuzneczow.settings.impl.view_model.SettingsScreenViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun SettingsScreenRoot(
    titleTextState: MutableState<String>,
) {
    titleTextState.value = stringResource(R.string.feature_settings_title)
    val dvm: SettingsScreenDependenciesViewModel = viewModel()
    val svm: SettingsScreenViewModel = viewModel(factory = SettingsScreenViewModel.Factory(dvm.dispatchersApi, dvm.settingsRepositoryApi))
    val state: State<SettingsScreenState> = svm.collectAsState()
    SettingsScreen(
        state = state.value,
        changeThemeState = { svm.dispatch(SettingsScreenIntent.ChangeThemeState(it)) },
    )
}