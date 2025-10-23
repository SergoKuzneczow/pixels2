package com.sergokuzneczow.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.icons.ArrowLeft
import com.sergokuzneczow.core.ui.icons.Download
import com.sergokuzneczow.core.ui.icons.FailureVariant
import com.sergokuzneczow.core.ui.icons.Favorites
import com.sergokuzneczow.core.ui.icons.Filter
import com.sergokuzneczow.core.ui.icons.Home
import com.sergokuzneczow.core.ui.icons.Information
import com.sergokuzneczow.core.ui.icons.SuccessVariant
import com.sergokuzneczow.core.ui.icons.New
import com.sergokuzneczow.core.ui.icons.Search
import com.sergokuzneczow.core.ui.icons.Selector
import com.sergokuzneczow.core.ui.icons.Settings
import com.sergokuzneczow.core.ui.icons.Top
import com.sergokuzneczow.core.ui.icons.Views
import com.sergokuzneczow.core.utilites.ThemePreviews

public object PixelsIcons {
    public val new: ImageVector = PixelsIcons.New
    public val topList: ImageVector = PixelsIcons.Top
    public val views: ImageVector = PixelsIcons.Views
    public val favorites: ImageVector = PixelsIcons.Favorites
    public val home: ImageVector = PixelsIcons.Home
    public val arrowBack: ImageVector = PixelsIcons.ArrowLeft
    public val settings: ImageVector = PixelsIcons.Settings
    public val filter: ImageVector = PixelsIcons.Filter
    public val search: ImageVector = PixelsIcons.Search
    public val information: ImageVector = PixelsIcons.Information
    public val selector: ImageVector = PixelsIcons.Selector
    public val download: ImageVector = PixelsIcons.Download
    public val successVariant: ImageVector = PixelsIcons.SuccessVariant
    public val failureVariant: ImageVector = PixelsIcons.FailureVariant
}


@ThemePreviews
@Composable
private fun Favorites() {
    Image(
        imageVector = PixelsIcons.favorites,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun TopList() {
    Image(
        imageVector = PixelsIcons.topList,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun DateAdded() {
    Image(
        imageVector = PixelsIcons.new,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun Views() {
    Image(
        imageVector = PixelsIcons.views,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun Home() {
    Image(
        imageVector = PixelsIcons.home,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun ArrowBack() {
    Image(
        imageVector = PixelsIcons.arrowBack,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun Settings() {
    Image(
        imageVector = PixelsIcons.settings,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}


@ThemePreviews
@Composable
private fun Filter() {
    Image(
        imageVector = PixelsIcons.filter,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun Search() {
    Image(
        imageVector = PixelsIcons.search,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun Information() {
    Image(
        imageVector = PixelsIcons.information,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun Selector() {
    Image(
        imageVector = PixelsIcons.selector,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun Download() {
    Image(
        imageVector = PixelsIcons.download,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun SuccessVariant() {
    Image(
        imageVector = PixelsIcons.successVariant,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}

@ThemePreviews
@Composable
private fun FailureVariant() {
    Image(
        imageVector = PixelsIcons.failureVariant,
        contentDescription = null,
        modifier = Modifier.size(64.dp)
    )
}