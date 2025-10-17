package com.sergokuzneczow.settings.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.settings.R
import com.sergokuzneczow.settings.impl.ui.SettingsScreen
import com.sergokuzneczow.settings.impl.view_model.SettingsScreenViewModel
import com.sergokuzneczow.settings.impl.view_model.SettingsScreenViewModelFactory

@Composable
internal fun SettingsScreenRoot(
    titleTextState: MutableState<String>,
) {
    titleTextState.value = stringResource(R.string.feature_settings_title)
    val vm: SettingsScreenViewModel = viewModel(factory = SettingsScreenViewModelFactory(LocalContext.current))
    val uiState: SettingsScreenUiState by vm.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        changeThemeState = { applicationSettings: ApplicationSettings -> vm.setIntent(SettingsScreenIntent.ChangeSettingsIntent(newApplicationSettings = applicationSettings)) }
    )
}