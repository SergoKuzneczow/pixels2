package com.sergokuzneczow.settings.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.settings.R

@Composable
internal fun SettingsScreenRoot(
    titleTextState: MutableState<String>,
    progressBarIsVisible: MutableState<Boolean>,
) {
    titleTextState.value = stringResource(R.string.feature_settings_title)
    SettingsScreen()
}