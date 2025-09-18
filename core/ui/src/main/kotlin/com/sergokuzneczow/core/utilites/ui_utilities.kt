package com.sergokuzneczow.core.utilites

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class ThemePreviews

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showSystemUi = true)
annotation class ThemeUiPreviews