package com.sergokuzneczow.settings.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.settings.R

@Composable
internal fun SettingsScreenRoot(
    titleState: MutableState<String>,
    showProgressBar: (visible: Boolean) -> Unit,
) {
    titleState.value = stringResource(R.string.feature_settings_title)
    SettingsScreen()
}