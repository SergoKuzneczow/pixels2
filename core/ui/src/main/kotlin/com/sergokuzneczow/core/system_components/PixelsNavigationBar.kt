package com.sergokuzneczow.core.system_components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sergokuzneczow.core.ui.PixelsIcons
import com.sergokuzneczow.core.ui.PixelsTheme
import com.sergokuzneczow.core.utilites.ThemePreviews

@Composable
public fun RowScope.PixelsNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = PixelsNavigationDefaults.selected(),
            unselectedIconColor = PixelsNavigationDefaults.unselected(),
            selectedTextColor = PixelsNavigationDefaults.unselected(),
            unselectedTextColor = PixelsNavigationDefaults.unselected(),
            indicatorColor = PixelsNavigationDefaults.indicator(),
        ),
    )
}

@Composable
public fun PixelsNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        content = content,
    )
}

@Composable
public fun PixelsNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = PixelsNavigationDefaults.selected(),
            unselectedIconColor = PixelsNavigationDefaults.unselected(),
            selectedTextColor = PixelsNavigationDefaults.unselected(),
            unselectedTextColor = PixelsNavigationDefaults.unselected(),
            indicatorColor = PixelsNavigationDefaults.indicator(),
        ),
    )
}

@Composable
public fun PixelsNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = PixelsNavigationDefaults.indicator(),
        header = header,
        content = content,
    )
}

@Composable
public fun PixelsScaffold(
    navigationSuiteItems: PixelsNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit,
) {
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = PixelsNavigationDefaults.selected(),
            unselectedIconColor = PixelsNavigationDefaults.unselected(),
            selectedTextColor = PixelsNavigationDefaults.unselected(),
            unselectedTextColor = PixelsNavigationDefaults.unselected(),
            indicatorColor = PixelsNavigationDefaults.indicator(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = PixelsNavigationDefaults.selected(),
            unselectedIconColor = PixelsNavigationDefaults.unselected(),
            selectedTextColor = PixelsNavigationDefaults.unselected(),
            unselectedTextColor = PixelsNavigationDefaults.unselected(),
            indicatorColor = PixelsNavigationDefaults.indicator(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = PixelsNavigationDefaults.selected(),
            unselectedIconColor = PixelsNavigationDefaults.unselected(),
            selectedTextColor = PixelsNavigationDefaults.unselected(),
            unselectedTextColor = PixelsNavigationDefaults.unselected(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            PixelsNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = PixelsNavigationDefaults.indicator(),
            navigationRailContainerColor = Color.Transparent,
        ),
        modifier = modifier,
    ) {
        content()
    }
}

public class PixelsNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    public fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ): Unit = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

@ThemePreviews
@Composable
private fun PixelsNavigationBarPreview() {
    val items = listOf("Home", "Settings")
    val icons = listOf(
        PixelsIcons.home,
        PixelsIcons.settings,
    )
    val selectedIcons = listOf(
        PixelsIcons.home,
        PixelsIcons.settings,
    )

    PixelsTheme {
        PixelsNavigationBar {
            items.forEachIndexed { index, item ->
                PixelsNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun PixelsNavigationRailPreview() {
    val items = listOf("Home", "Settings")
    val icons = listOf(
        PixelsIcons.home,
        PixelsIcons.settings,
    )
    val selectedIcons = listOf(
        PixelsIcons.home,
        PixelsIcons.settings,
    )

    PixelsTheme {
        PixelsNavigationRail {
            items.forEachIndexed { index, item ->
                PixelsNavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

private object PixelsNavigationDefaults {

    @Composable
    fun selected() = MaterialTheme.colorScheme.inverseOnSurface

    @Composable
    fun unselected() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun indicator() = MaterialTheme.colorScheme.secondaryContainer
}