package com.sergokuzneczow.settings.impl

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sergokuzneczow.core.ui.PixelsTheme

@Composable
internal fun SettingsScreen() {

}

@Composable
@Preview(
    name = "Light",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
public fun SettingsScreenPreview() {
    PixelsTheme {
        Surface {
            SettingsScreen()
        }
    }
}