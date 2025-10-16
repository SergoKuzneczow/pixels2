package com.sergokuzneczow.home.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.ui.Dimensions
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews
import com.sergokuzneczow.home.impl.HomeListUiState
import com.sergokuzneczow.home.impl.StandardQuery
import com.sergokuzneczow.home.impl.SuggestedQueriesPage
import com.sergokuzneczow.home.impl.SuggestedQueriesPage.SuggestedQuery
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

private val PADDING_BETWEEN_BLOCKS: Dp = 24.dp
private val ITEM_PADDINGS: Dp = 4.dp

@Composable
internal fun HomeList(
    homeListUiState: HomeListUiState,
    itemClick: (PageQuery, PageFilter) -> Unit,
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
        when (homeListUiState) {
            is HomeListUiState.Loading -> {
                item {
                    StandardQueries(
                        standardQueries = homeListUiState.standardQuery,
                        itemClick = itemClick,
                    )
                }
            }

            is HomeListUiState.Success -> {
                item {
                    StandardQueries(
                        standardQueries = homeListUiState.standardQuery,
                        itemClick = itemClick,
                    )
                }
                item { Spacer(modifier = Modifier.height(PADDING_BETWEEN_BLOCKS)) }
                itemsIndexed(homeListUiState.suggestedQueriesPages) { position, page ->
                    SuggestedQueriesPage(
                        suggestedQueriesPage = page,
                        itemClick = itemClick,
                    )
                    if (homeListUiState.suggestedQueriesPages.size - 4 < position) nextPage.invoke()
                }
            }
        }
    }
}

@Composable
private fun StandardQueries(
    standardQueries: List<StandardQuery>,
    itemClick: (PageQuery, PageFilter) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        standardQueries.forEach { standardQuery ->
            Column(
                modifier = Modifier
                    .width(72.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .clickable(onClick = { itemClick.invoke(standardQuery.pageQuery, standardQuery.pageFilter) })
            ) {
                Image(
                    imageVector = standardQuery.vectorIcon,
                    contentDescription = standardQuery.description,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = standardQuery.description,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun SuggestedQueriesPage(
    suggestedQueriesPage: SuggestedQueriesPage,
    itemClick: (PageQuery, PageFilter) -> Unit,
) {
    val pageItems: List<SuggestedQuery?> = suggestedQueriesPage.items
    val rowSize: Int = calculateRowSize(totalSize = pageItems.size)
    val itemsForRow: List<List<SuggestedQuery?>> = pageItems.chunked(rowSize)
    itemsForRow.forEach { rowItems: List<SuggestedQuery?> ->
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            rowItems.forEach { item: SuggestedQuery? ->
                Box(
                    modifier = Modifier
                        .padding(ITEM_PADDINGS)
                        .weight(1f)
                        .size(164.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .clickable(onClick = { if (item != null) itemClick.invoke(item.pageQuery, item.pageFilter) })
                ) {
                    if (item != null) PictureItem(item.previewPath, item.description)
                    else PixelsCircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun BoxScope.PictureItem(previewPath: String, description: String) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.3f)
                    .background(Color.Black)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.titleMedium,
                color = Color(245, 244, 244, 255),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
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
    return when {
        windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> {
            when {
                totalSize % 5 == 0 -> 5
                totalSize % 4 == 0 -> 4
                totalSize % 3 == 0 -> 3
                totalSize % 2 == 0 -> 2
                else -> 1
            }
        }

        windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
            when {
                totalSize % 4 == 0 -> 4
                totalSize % 3 == 0 -> 3
                totalSize % 2 == 0 -> 2
                else -> 1
            }
        }

        else -> {
            when {
                totalSize % 3 == 0 -> 3
                totalSize % 2 == 0 -> 2
                else -> 1
            }
        }
    }
}

@ThemePreviews
@Composable
private fun StandardQueriesPreview() {
    PixelsTheme {
        StandardQueries(
            standardQueries = HomeListUiState.Loading().standardQuery,
            itemClick = { _, _ -> }
        )
    }
}

@ThemePreviews
@Composable
private fun SuggestedQueriesPagePreview() {
    val suggestedQueries: List<SuggestedQuery> = listOf(
        SuggestedQuery(
            description = "Preview",
            previewPath = "",
            pageQuery = PageQuery.DEFAULT,
            pageFilter = PageFilter.DEFAULT,
        ),
        SuggestedQuery(
            description = "Preview",
            previewPath = "",
            pageQuery = PageQuery.DEFAULT,
            pageFilter = PageFilter.DEFAULT,
        ),
        SuggestedQuery(
            description = "Preview",
            previewPath = "",
            pageQuery = PageQuery.DEFAULT,
            pageFilter = PageFilter.DEFAULT,
        ),
        SuggestedQuery(
            description = "Preview",
            previewPath = "",
            pageQuery = PageQuery.DEFAULT,
            pageFilter = PageFilter.DEFAULT,
        ),
        SuggestedQuery(
            description = "Preview",
            previewPath = "",
            pageQuery = PageQuery.DEFAULT,
            pageFilter = PageFilter.DEFAULT,
        ),
        SuggestedQuery(
            description = "Preview",
            previewPath = "",
            pageQuery = PageQuery.DEFAULT,
            pageFilter = PageFilter.DEFAULT,
        ),
    )
    PixelsTheme {
        SuggestedQueriesPage(
            suggestedQueriesPage = SuggestedQueriesPage(items = suggestedQueries),
            itemClick = { _, _ -> }
        )
    }
}

@ThemePreviews
@Composable
private fun HomeListPreview() {
    val homeListUiState = HomeListUiState.Success(
        suggestedQueriesPages = listOf(
            SuggestedQueriesPage(
                items = listOf(
                    SuggestedQuery(
                        description = "",
                        previewPath = "",
                        pageQuery = PageQuery.DEFAULT,
                        pageFilter = PageFilter.DEFAULT,
                    )
                )
            )
        )
    )
    PixelsTheme {
        HomeList(
            homeListUiState = homeListUiState,
            itemClick = { _, _ -> },
            nextPage = {},
        )
    }
}