package com.sergokuzneczow.search_suitable_pictures.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.PixelsSearchTextField
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.search_suitable_pictures.impl.SearchSuitablePicturesScreenState

@Composable
internal fun SearchSuitablePicturesScreen(
    state: SearchSuitablePicturesScreenState,
    onSearchFieldChange: (value: String) -> Unit,
    onSearchDone: (value: String) -> Unit,
) {
    when (state) {
        is SearchSuitablePicturesScreenState.SearchField -> {
            Box(modifier = Modifier.fillMaxSize()) {
                PixelsSearchTextField(
                    value = state.searchField,
                    onValueChange = { if (it != state.searchField) onSearchFieldChange.invoke(it) },
                    onDone = { onSearchDone.invoke(it) },
                    modifier = Modifier.padding(horizontal = Dimensions.LargePadding)
                )
            }
        }
    }
}
