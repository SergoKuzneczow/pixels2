package com.sergokuzneczow.selected_picture.impl.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sergokuzneczow.core.system_components.PixelsPrimaryFloatingActionButton
import com.sergokuzneczow.core.ui.PixelsIcons

@Composable
internal fun BoxScope.PictureInformationFloatingActionButton(
    fabVisible: Boolean,
    onFabClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = fabVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .align(Alignment.BottomEnd)
    ) {
        PixelsPrimaryFloatingActionButton(
            imageVector = PixelsIcons.information,
            onCLick = { onFabClick.invoke() }
        )
    }
}