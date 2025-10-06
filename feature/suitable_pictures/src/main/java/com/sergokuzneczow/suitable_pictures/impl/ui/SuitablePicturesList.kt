package com.sergokuzneczow.suitable_pictures.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesPage
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesUiState

private val ITEM_PADDINGS: Dp = 4.dp
private val BOX_CONTENT_SIZE: Dp = 164.dp

@Composable
internal fun SuitablePicturesList(
    uiStateSuccess: SuitablePicturesUiState.Success,
    onItemClick: (pictureKey: String) -> Unit,
    nextPage: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = Dimensions.ContentPadding,
            end = Dimensions.ContentPadding,
            top = Dimensions.PixelsTopBarBoxHeight + Dimensions.ContentPadding,
            bottom = Dimensions.ContentPadding
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        val pages: List<SuitablePicturesPage> = uiStateSuccess.suitablePicturesPages
        itemsIndexed(pages) { position: Int, page: SuitablePicturesPage ->
            SuggestedQueriesPage(
                pageItems = page.items,
                onItemClick = onItemClick,
            )
            if (pages.size - 2 < position) nextPage.invoke()
        }
    }
}

@Composable
private fun SuggestedQueriesPage(
    pageItems: List<SuitablePicturesPage.SuitablePicture?>,
    onItemClick: (pictureKey: String) -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val itemsForRow: List<List<SuitablePictureListItem>> = pageItems.toRows(windowWidthSizeClass = windowAdaptiveInfo.windowSizeClass.windowWidthSizeClass)

    itemsForRow.forEach { rowItems: List<SuitablePictureListItem> ->
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            rowItems.forEach { item: SuitablePictureListItem ->
                when (item) {
                    is SuitablePictureListItem.Picture -> {
                        when {
                            item.picture != null -> {
                                Box(
                                    modifier = Modifier
                                        .padding(ITEM_PADDINGS)
                                        .weight(1f)
                                        .size(BOX_CONTENT_SIZE)
                                        .clip(Dimensions.PixelsShape)
                                        .background(MaterialTheme.colorScheme.surfaceContainer)
                                        .clickable(onClick = { onItemClick.invoke(item.picture.pictureKey) })
                                ) {
                                    PictureItem(item.picture.previewPath)
                                }
                            }

                            else -> {
                                Box(
                                    modifier = Modifier
                                        .padding(ITEM_PADDINGS)
                                        .weight(1f)
                                        .size(BOX_CONTENT_SIZE)
                                        .clip(Dimensions.PixelsShape)
                                        .background(MaterialTheme.colorScheme.surfaceContainer)
                                ) {
                                    PixelsCircularProgressIndicator()
                                }
                            }
                        }
                    }

                    is SuitablePictureListItem.Placeholder -> {
                        Box(
                            modifier = Modifier
                                .padding(ITEM_PADDINGS)
                                .weight(1f)
                                .size(BOX_CONTENT_SIZE)
                        )
                    }
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
                modifier = Modifier.fillMaxSize()
            )
        }

        false -> PixelsCircularProgressIndicator()
    }
}

private fun List<SuitablePicturesPage.SuitablePicture?>.toRows(windowWidthSizeClass: WindowWidthSizeClass): List<List<SuitablePictureListItem>> {
    var res: List<SuitablePictureListItem> = this.map { SuitablePictureListItem.Picture(picture = it) }
    val rowSizeWithoutPlaceholders: Int? = res.tryCalculateRowSizeWithoutPlaceholders(windowWidthSizeClass = windowWidthSizeClass)
    if (rowSizeWithoutPlaceholders != null) {
        return res.chunked(rowSizeWithoutPlaceholders)
    } else {
        return when (windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> {
                val rowSize = 3
                while (res.size % rowSize != 0) {
                    res = res + SuitablePictureListItem.Placeholder
                }
                res.chunked(rowSize)
            }

            WindowWidthSizeClass.MEDIUM -> {
                val rowSize = 4
                while (res.size % rowSize != 0) {
                    res = res + SuitablePictureListItem.Placeholder
                }
                res.chunked(rowSize)
            }

            WindowWidthSizeClass.EXPANDED -> {
                val rowSize = 4
                while (res.size % rowSize != 0) {
                    res = res + SuitablePictureListItem.Placeholder
                }
                res.chunked(rowSize)
            }

            else -> res.chunked(1)
        }
    }
}

private fun List<SuitablePictureListItem>.tryCalculateRowSizeWithoutPlaceholders(windowWidthSizeClass: WindowWidthSizeClass): Int? {
    return when (windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            when {
                this.size % 3 == 0 -> 3
                else -> null
            }
        }

        WindowWidthSizeClass.MEDIUM -> {
            when {
                this.size % 5 == 0 -> 5
                this.size % 4 == 0 -> 4
                this.size % 3 == 0 -> 3
                else -> null
            }
        }

        WindowWidthSizeClass.EXPANDED -> {
            when {
                this.size % 5 == 0 -> 5
                this.size % 4 == 0 -> 4
                this.size % 3 == 0 -> 3
                else -> null
            }
        }

        else -> null
    }
}

private sealed interface SuitablePictureListItem {
    data class Picture(val picture: SuitablePicturesPage.SuitablePicture?) : SuitablePictureListItem
    data object Placeholder : SuitablePictureListItem
}

@ThemePreviews
@Composable
private fun SuitablePicturesListPreview() {
    val page = SuitablePicturesPage(
        items = listOf(
            SuitablePicturesPage.SuitablePicture(pictureKey = "key", previewPath = "path"),
        )
    )
    val suitablePicturesListUiState = SuitablePicturesUiState.Success(
        suitablePicturesPages = listOf(page)
    )
    PixelsTheme {
        SuitablePicturesList(
            uiStateSuccess = suitablePicturesListUiState,
            onItemClick = {},
            nextPage = {}
        )
    }
}