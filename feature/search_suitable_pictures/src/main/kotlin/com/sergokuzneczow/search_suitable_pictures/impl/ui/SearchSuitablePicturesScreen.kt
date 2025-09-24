package com.sergokuzneczow.search_suitable_pictures.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.PixelsSearchTextField
import com.sergokuzneczow.core.ui.Dimensions

@Composable
internal fun SearchSuitablePicturesScreen(
    searchDone: (queryWord: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PixelsSearchTextField(
            done = { searchDone.invoke(it) },
            modifier = Modifier.padding(horizontal = Dimensions.LargePadding)
        )
    }
}
