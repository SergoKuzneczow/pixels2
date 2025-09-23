package com.sergokuzneczow.core.ui

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import com.sergokuzneczow.core.ui.icons.Favorites
import com.sergokuzneczow.core.ui.icons.Filter
import com.sergokuzneczow.core.ui.icons.New
import com.sergokuzneczow.core.ui.icons.Top
import com.sergokuzneczow.core.ui.icons.Views
import com.sergokuzneczow.core.utilites.ThemePreviews

object PixelsIcons {
    val home = Icons.Rounded.Home
    val settings = Icons.Rounded.Settings
    val new = PixelsIcons.New
    val topList = PixelsIcons.Top
    val views = PixelsIcons.Views
    val favorites = PixelsIcons.Favorites
    val filter = PixelsIcons.Filter
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