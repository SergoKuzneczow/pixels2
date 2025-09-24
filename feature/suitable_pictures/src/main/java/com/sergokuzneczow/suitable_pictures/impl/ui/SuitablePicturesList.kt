package com.sergokuzneczow.suitable_pictures.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.suitable_pictures.R.string.empty_collection
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesUiState
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesUiState.SuitablePicture

private val ITEM_PADDINGS: Dp = 4.dp

@Composable
internal fun SuitablePicturesList(
    suitablePicturesListUiState: SuitablePicturesUiState,
    onItemClick: () -> Unit,
    nextPage: () -> Unit,
) {
    when (suitablePicturesListUiState) {
        is SuitablePicturesUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                PixelsCircularProgressIndicator()
            }
        }

        is SuitablePicturesUiState.Empty -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(empty_collection),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is SuitablePicturesUiState.Success -> {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = Dimensions.ContentPadding,
                    end = Dimensions.ContentPadding,
                    top = Dimensions.PixelsTopBarBoxHeight + Dimensions.ContentPadding,
                    bottom = Dimensions.ContentPadding
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                val pages: List<SuitablePicturesUiState.SuitablePicturesPage> = suitablePicturesListUiState.suitablePicturesPages
                itemsIndexed(pages) { position: Int, page: SuitablePicturesUiState.SuitablePicturesPage ->
                    SuggestedQueriesPage(
                        pageItems = page.items,
                        onItemClick = onItemClick,
                    )
                    if (pages.size - 2 < position) nextPage.invoke()
                }
            }
        }
    }
}

@Composable
private fun SuggestedQueriesPage(
    pageItems: List<SuitablePicture?>,
    onItemClick: () -> Unit,
) {
    val rowSize: Int = calculateRowSize(totalSize = pageItems.size)
    val itemsForRow: List<List<SuitablePicture?>> = pageItems.chunked(rowSize)
    itemsForRow.forEach { rowItems: List<SuitablePicture?> ->
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            rowItems.forEach { item: SuitablePicture? ->
                Box(
                    modifier = Modifier
                        .padding(ITEM_PADDINGS)
                        .weight(1f)
                        .size(164.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                    /*.clickable(onClick = { if (item != null) itemClick.invoke(item.pageQuery, item.pageFilter) })*/
                ) {
                    if (item != null) PictureItem(item.previewPath)
                    else PixelsCircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun BoxScope.PictureItem(previewPath: String) {
    val painter: AsyncImagePainter = rememberAsyncImagePainter(previewPath)
    val state: AsyncImagePainter.State by painter.state.collectAsStateWithLifecycle()
    var success: Boolean by rememberSaveable { mutableStateOf(false) }
    when (state) {
        is AsyncImagePainter.State.Empty -> success = false
        is AsyncImagePainter.State.Loading -> success = false
        is AsyncImagePainter.State.Success -> success = true
        is AsyncImagePainter.State.Error -> painter.restart()
    }
    when (success) {
        true -> {
            Image(
                painter = painter,
                contentDescription = previewPath,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        false -> PixelsCircularProgressIndicator()
    }
}

@Composable
private fun calculateRowSize(
    totalSize: Int,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
): Int {
    return when (windowAdaptiveInfo.windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            when {
                totalSize % 3 == 0 -> 3
                totalSize % 2 == 0 -> 2
                else -> 1
            }
        }

        WindowWidthSizeClass.MEDIUM -> {
            when {
                totalSize % 5 == 0 -> 5
                totalSize % 4 == 0 -> 4
                totalSize % 3 == 0 -> 3
                totalSize % 2 == 0 -> 2
                else -> 1
            }
        }

        WindowWidthSizeClass.EXPANDED -> {
            when {
                totalSize % 5 == 0 -> 5
                totalSize % 4 == 0 -> 4
                totalSize % 3 == 0 -> 3
                totalSize % 2 == 0 -> 2
                else -> 1
            }
        }

        else -> 1
    }
}