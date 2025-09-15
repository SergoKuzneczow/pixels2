package com.sergokuzneczow.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sergokuzneczow.core.system_components.PixelsCircularProgressIndicator
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

private val MIN_PICTURE_WIDTH: Dp = 150.dp
private val PICTURE_HEIGHT: Dp = 130.dp
private val PICTURE_PADDINGS: Dp = 4.dp

@Composable
internal fun SuggestedQueriesList(
    suggestedQueriesUiState: SuggestedQueriesUiState,
    suggestedQueryClick: () -> Unit,
    nextPage: () -> Unit,
) {
    when (suggestedQueriesUiState) {
        is SuggestedQueriesUiState.Empty -> {
            Box(modifier = Modifier.fillMaxSize()) { Text(text = "Empty collection") }
        }

        is SuggestedQueriesUiState.Success -> {
            SuggestedQueriesGrid(
                suggestedQueries = suggestedQueriesUiState.suggestedQueries,
                suggestedQueryClick = suggestedQueryClick,
                nextPage = nextPage,
            )
        }

        is SuggestedQueriesUiState.Default -> {
            SuggestedQueriesGrid(
                suggestedQueries = suggestedQueriesUiState.suggestedQueries,
                suggestedQueryClick = suggestedQueryClick,
                nextPage = nextPage,
            )
        }
    }
}

@Composable
private fun SuggestedQueriesGrid(
    suggestedQueries: List<SuggestedQueriesUiState.SuggestedQuery?>,
    suggestedQueryClick: () -> Unit,
    nextPage: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(MIN_PICTURE_WIDTH + (PICTURE_PADDINGS * 2)),
        contentPadding = PaddingValues(start = 4.dp, end = 4.dp, top = 112.dp),
        state = rememberLazyGridState(),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(suggestedQueries) { position, suggestedQuery ->
            suggestedQuery?.let {
                SuggestedQueriesGridItem(
                    description = suggestedQuery.description,
                    previewPath = suggestedQuery.previewPath,
                    suggestedQueryClick = suggestedQueryClick,
                )
            } ?: SuggestedQueriesGridItem()
            if (suggestedQueries.size - 6 < position) nextPage.invoke()
        }
    }
}

@Composable
private fun SuggestedQueriesGridItem(
    description: String? = null,
    previewPath: String? = null,
    suggestedQueryClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(PICTURE_PADDINGS)
            .fillMaxWidth()
            .height(PICTURE_HEIGHT)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = { if (previewPath != null && description != null) suggestedQueryClick.invoke() })
    ) {
        if (previewPath != null && description != null) {
            PictureItem(previewPath)
            Text(
                text = description,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        } else PixelsCircularProgressIndicator()
    }
}

@Composable
private fun BoxScope.PictureItem(previewPath: String) {
    val painter: AsyncImagePainter = rememberAsyncImagePainter(previewPath)
    val state: AsyncImagePainter.State by painter.state.collectAsState()
    when (state) {
        is AsyncImagePainter.State.Empty -> {
            PixelsCircularProgressIndicator()
        }

        is AsyncImagePainter.State.Loading -> {
            PixelsCircularProgressIndicator()
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = previewPath,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PICTURE_HEIGHT)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(PICTURE_HEIGHT)
                    .alpha(0.3f)
                    .background(Color.Black)
            )
        }

        is AsyncImagePainter.State.Error -> {
            painter.restart()
        }
    }
}

@ThemePreviews
@Composable
public fun SuggestedQueriesListPreview() {
    PixelsTheme {
        Surface {
            SuggestedQueriesList(
                suggestedQueriesUiState = SuggestedQueriesUiState.Success(emptyList()),
                suggestedQueryClick = {},
                nextPage = {},
            )
        }
    }
}

@ThemePreviews
@Composable
public fun SuggestedQueryItemPreview() {
    PixelsTheme {
        Surface {
            SuggestedQueriesGridItem(
                description = "Preview",
                previewPath = "null",
                suggestedQueryClick = {}
            )
        }
    }
}