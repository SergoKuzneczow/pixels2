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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_LARGE_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import coil3.ImageLoader
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sergokuzneczow.core.system_components.progress_indicators.PixelsProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesState
import com.sergokuzneczow.suitable_pictures.impl.model.SuitablePicturesPage

private val ITEM_PADDINGS: Dp = 4.dp
private val BOX_CONTENT_SIZE: Dp = 164.dp

@Composable
internal fun SuitablePicturesList(
    stateSuccess: SuitablePicturesState.Success,
    imageLoader: ImageLoader,
    onPictureClick: (pictureKey: String) -> Unit,
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
        val pages: List<SuitablePicturesPage> = stateSuccess.suitablePicturesPages
        itemsIndexed(pages) { position: Int, page: SuitablePicturesPage ->
            SuitablePicturesPage(
                pageItems = page.items,
                imageLoader = imageLoader,
                onItemClick = onPictureClick,
            )
            if (pages.size - 2 < position) nextPage.invoke()
        }
    }
}

@Composable
private fun SuitablePicturesPage(
    pageItems: List<SuitablePicturesPage.SuitablePicture?>,
    imageLoader: ImageLoader,
    onItemClick: (pictureKey: String) -> Unit,
) {
    val itemsForRow: List<List<SuitablePictureListItem>> = pageItems.toRows()

    itemsForRow.forEach { rowItems: List<SuitablePictureListItem> ->
        Row(modifier = Modifier.fillMaxWidth()) {
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
                                        .clickable(onClick = { onItemClick.invoke(item.picture.pictureKey) }),
                                    content = { PictureItem(item.picture.previewPath, imageLoader) }
                                )
                            }

                            else -> {
                                Box(
                                    modifier = Modifier
                                        .padding(ITEM_PADDINGS)
                                        .weight(1f)
                                        .size(BOX_CONTENT_SIZE)
                                        .clip(Dimensions.PixelsShape)
                                        .background(MaterialTheme.colorScheme.surfaceContainer),
                                    content = { PixelsProgressIndicator(Dimensions.SmallProgressBarSize) }
                                )
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
private fun BoxScope.PictureItem(previewPath: String, imageLoader: ImageLoader) {
    val painter: AsyncImagePainter = rememberAsyncImagePainter(previewPath, imageLoader)
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

        false -> PixelsProgressIndicator(Dimensions.SmallProgressBarSize)
    }
}

@Composable
private fun List<SuitablePicturesPage.SuitablePicture?>.toRows(windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()): List<List<SuitablePictureListItem>> {
    val res: List<SuitablePictureListItem> = this.map { SuitablePictureListItem.Picture(picture = it) }
    val rowSizeWithoutPlaceholders: Int? = res.tryCalculateRowSizeWithoutPlaceholders(windowAdaptiveInfo)
    return if (rowSizeWithoutPlaceholders != null) res.chunked(rowSizeWithoutPlaceholders)
    else res.calculateRowSizeWithPlaceholders(windowAdaptiveInfo)
}

private fun List<SuitablePictureListItem>.calculateRowSizeWithPlaceholders(windowAdaptiveInfo: WindowAdaptiveInfo): List<List<SuitablePictureListItem>> {
    var res: List<SuitablePictureListItem> = this
    return when {
        windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> {
            val rowSize = 6
            while (res.size % rowSize != 0) {
                res = res + SuitablePictureListItem.Placeholder
            }
            res.chunked(rowSize)
        }

        windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
            val rowSize = 4
            while (res.size % rowSize != 0) {
                res = res + SuitablePictureListItem.Placeholder
            }
            res.chunked(rowSize)
        }

        else -> {
            val rowSize = 3
            while (res.size % rowSize != 0) {
                res = res + SuitablePictureListItem.Placeholder
            }
            res.chunked(rowSize)
        }
    }
}

private fun List<SuitablePictureListItem>.tryCalculateRowSizeWithoutPlaceholders(windowAdaptiveInfo: WindowAdaptiveInfo): Int? {
    return when {
        windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_LARGE_LOWER_BOUND) -> {
            when {
                this.size % 6 == 0 -> 6
                this.size % 5 == 0 -> 5
                else -> null
            }
        }

        windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
            when {
                this.size % 4 == 0 -> 4
                else -> null
            }
        }

        else -> {
            when {
                this.size % 3 == 0 -> 3
                else -> null
            }
        }
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
    val suitablePicturesListUiState = SuitablePicturesState.Success(
        title = "Default",
        suitablePicturesPages = listOf(page),
    )
    PixelsTheme {
        SuitablePicturesList(
            stateSuccess = suitablePicturesListUiState,
            imageLoader = ImageLoader(LocalContext.current),
            onPictureClick = {},
            nextPage = {},
        )
    }
}