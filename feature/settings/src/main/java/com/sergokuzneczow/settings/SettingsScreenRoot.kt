package com.sergokuzneczow.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
internal fun SettingsScreenRoot(
    changeTitle: (title: String) -> Unit,
    showProgressBar: (visible: Boolean) -> Unit,
) {
    changeTitle.invoke(stringResource(R.string.feature_settings_title))
    SettingsScreen()
}