package com.sergokuzneczow.core.ui

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.sergokuzneczow.core.ui.icons.Favorites
import com.sergokuzneczow.core.ui.icons.Filter
import com.sergokuzneczow.core.ui.icons.Information
import com.sergokuzneczow.core.ui.icons.New
import com.sergokuzneczow.core.ui.icons.Search
import com.sergokuzneczow.core.ui.icons.Top
import com.sergokuzneczow.core.ui.icons.Views
import com.sergokuzneczow.core.utilites.ThemePreviews

public object PixelsIcons {
    public val home: ImageVector = Icons.Rounded.Home
    public val settings: ImageVector  = Icons.Rounded.Settings
    public val new: ImageVector  = PixelsIcons.New
    public val topList: ImageVector  = PixelsIcons.Top
    public val views: ImageVector  = PixelsIcons.Views
    public val favorites: ImageVector  = PixelsIcons.Favorites
    public val filter: ImageVector  = PixelsIcons.Filter
    public val search: ImageVector  = PixelsIcons.Search
    public val information: ImageVector  = PixelsIcons.Information
}

@ThemePreviews
@Composable
private fun Home() {
    Image(imageVector = PixelsIcons.home, contentDescription = null)
}

@ThemePreviews
@Composable
private fun Settings() {
    Image(imageVector = PixelsIcons.settings, contentDescription = null)
}

@ThemePreviews
@Composable
private fun Favorites() {
    Image(imageVector = PixelsIcons.favorites, contentDescription = null)
}

@ThemePreviews
@Composable
private fun TopList() {
    Image(imageVector = PixelsIcons.topList, contentDescription = null)
}

@ThemePreviews
@Composable
private fun DateAdded() {
    Image(imageVector = PixelsIcons.new, contentDescription = null)
}

@ThemePreviews
@Composable
private fun Views() {
    Image(imageVector = PixelsIcons.views, contentDescription = null)
}

@ThemePreviews
@Composable
private fun Filter() {
    Image(imageVector = PixelsIcons.filter, contentDescription = null)
}

@ThemePreviews
@Composable
private fun Search() {
    Image(imageVector = PixelsIcons.search, contentDescription = null)
}

@ThemePreviews
@Composable
private fun Information() {
    Image(imageVector = PixelsIcons.information, contentDescription = null)
}