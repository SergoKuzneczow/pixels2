package com.sergokuzneczow.suitable_pictures.impl.ui

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
internal fun SuitablePicturesList(
    suitablePicturesUiState: SuitablePicturesUiState,
    onItemClick: (pictureKey: String) -> Unit,
    nextPage: () -> Unit,
) {
    when (suitablePicturesUiState) {
        is SuitablePicturesUiState.Empty -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Empty collection",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is SuitablePicturesUiState.Success -> {
            SuitablePicturesGrid(
                items = suitablePicturesUiState.suitablePictures,
                onItemClick = onItemClick,
                nextPage = nextPage,
            )
        }

        is SuitablePicturesUiState.Default -> {
            SuitablePicturesGrid(
                items = suitablePicturesUiState.suitablePictures,
                onItemClick = onItemClick,
                nextPage = nextPage,
            )
        }
    }
}

@Composable
private fun SuitablePicturesGrid(
    items: List<SuitablePicturesUiState.SuitablePicture?>,
    onItemClick: (pictureKey: String) -> Unit,
    nextPage: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(MIN_PICTURE_WIDTH + (PICTURE_PADDINGS * 2)),
        contentPadding = PaddingValues(start = 4.dp, end = 4.dp, top = 112.dp),
        state = rememberLazyGridState(),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(items) { position, suitablePicture ->
            suitablePicture?.let {
                SuitablePicturesGridItem(
                    suitablePicture = suitablePicture,
                    onItemClick = onItemClick,
                )
            } ?: SuitablePicturesGridItem(
                suitablePicture = null,
                onItemClick = {}
            )
            if (items.size - 6 < position) nextPage.invoke()
        }
    }
}

@Composable
private fun SuitablePicturesGridItem(
    suitablePicture: SuitablePicturesUiState.SuitablePicture? = null,
    onItemClick: (pictureKey: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(PICTURE_PADDINGS)
            .fillMaxWidth()
            .height(PICTURE_HEIGHT)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = { if (suitablePicture != null) onItemClick.invoke(suitablePicture.pictureKey) })
    ) {
        if (suitablePicture != null) {
            PictureItem(suitablePicture.previewPath)
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
            SuitablePicturesList(
                suitablePicturesUiState = SuitablePicturesUiState.Success(emptyList()),
                onItemClick = {},
                nextPage = {},
            )
        }
    }
}

@ThemePreviews
@Composable
public fun SuitablePicturesUiStateIsEmpty() {
    PixelsTheme {
        Surface {
            SuitablePicturesList(
                suitablePicturesUiState = SuitablePicturesUiState.Empty,
                onItemClick = {},
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
            SuitablePicturesGridItem(
                suitablePicture = SuitablePicturesUiState.SuitablePicture(
                    pictureKey = "",
                    previewPath = ""
                ),
                onItemClick = {}
            )
        }
    }
}