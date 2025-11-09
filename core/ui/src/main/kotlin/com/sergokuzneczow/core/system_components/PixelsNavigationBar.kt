package com.sergokuzneczow.core.system_components

import android.R.attr.label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
public fun PixelsNavigationSuiteScaffold(
    navigationSuiteItems: PixelsNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val layoutType: NavigationSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)

    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = PixelsNavigationDefaults.selectedContent(),
            unselectedIconColor = PixelsNavigationDefaults.content(),
            selectedTextColor = PixelsNavigationDefaults.content(),
            unselectedTextColor = PixelsNavigationDefaults.content(),
            indicatorColor = PixelsNavigationDefaults.accent(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = PixelsNavigationDefaults.selectedContent(),
            unselectedIconColor = PixelsNavigationDefaults.content(),
            selectedTextColor = PixelsNavigationDefaults.content(),
            unselectedTextColor = PixelsNavigationDefaults.content(),
            indicatorColor = PixelsNavigationDefaults.accent(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = PixelsNavigationDefaults.selectedContent(),
            unselectedIconColor = PixelsNavigationDefaults.content(),
            selectedTextColor = PixelsNavigationDefaults.content(),
            unselectedTextColor = PixelsNavigationDefaults.content(),
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
            navigationBarContentColor = PixelsNavigationDefaults.accent(),
            navigationRailContainerColor = Color.Transparent,
        ),
        modifier = modifier,
        content = content,
    )
}

//@Composable
//private fun RowScope.PixelsNavigationBarItem(
//    selected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    alwaysShowLabel: Boolean = true,
//    icon: @Composable () -> Unit,
//    selectedIcon: @Composable () -> Unit = icon,
//    label: @Composable (() -> Unit)? = null,
//) {
//    NavigationBarItem(
//        selected = selected,
//        onClick = onClick,
//        icon = if (selected) selectedIcon else icon,
//        modifier = modifier,
//        enabled = enabled,
//        label = label,
//        alwaysShowLabel = alwaysShowLabel,
//        colors = NavigationBarItemDefaults.colors(
//            selectedIconColor = PixelsNavigationDefaults.selectedContent(),
//            unselectedIconColor = PixelsNavigationDefaults.content(),
//            selectedTextColor = PixelsNavigationDefaults.content(),
//            unselectedTextColor = PixelsNavigationDefaults.content(),
//            indicatorColor = PixelsNavigationDefaults.accent(),
//        ),
//    )
//}

//@Composable
//private fun PixelsNavigationBar(
//    modifier: Modifier = Modifier,
//    content: @Composable RowScope.() -> Unit,
//) {
//    NavigationBar(
//        modifier = modifier,
//        containerColor = MaterialTheme.colorScheme.surface,
//        tonalElevation = 0.dp,
//        content = content,
//    )
//}

//@Composable
//private fun PixelsNavigationRailItem(
//    selected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    alwaysShowLabel: Boolean = true,
//    icon: @Composable () -> Unit,
//    selectedIcon: @Composable () -> Unit = icon,
//    label: @Composable (() -> Unit)? = null,
//) {
//    NavigationRailItem(
//        selected = selected,
//        onClick = onClick,
//        icon = if (selected) selectedIcon else icon,
//        modifier = modifier,
//        enabled = enabled,
//        label = label,
//        alwaysShowLabel = alwaysShowLabel,
//        colors = NavigationRailItemDefaults.colors(
//            selectedIconColor = PixelsNavigationDefaults.selectedContent(),
//            unselectedIconColor = PixelsNavigationDefaults.content(),
//            selectedTextColor = PixelsNavigationDefaults.content(),
//            unselectedTextColor = PixelsNavigationDefaults.content(),
//            indicatorColor = PixelsNavigationDefaults.accent(),
//        ),
//    )
//}

//@Composable
//private fun PixelsNavigationRail(
//    modifier: Modifier = Modifier,
//    header: @Composable (ColumnScope.() -> Unit)? = null,
//    content: @Composable ColumnScope.() -> Unit,
//) {
//    NavigationRail(
//        modifier = modifier,
//        containerColor = MaterialTheme.colorScheme.surface,
//        contentColor = PixelsNavigationDefaults.accent(),
//        header = header,
//        content = content,
//    )
//}

public class PixelsNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {

    public fun item(
        selected: Boolean,
        icon: @Composable () -> Unit,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        label: @Composable (() -> Unit)? = null,
        selectedIcon: @Composable () -> Unit = icon,
    ): Unit = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = { if (selected) selectedIcon.invoke() else icon.invoke() },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

//@ThemePreviews
//@Composable
//private fun PixelsNavigationBarPreview() {
//    val items = listOf("Home", "Settings")
//    val icons = listOf(
//        PixelsIcons.home,
//        PixelsIcons.settings,
//    )
//    val selectedIcons = listOf(
//        PixelsIcons.home,
//        PixelsIcons.settings,
//    )
//
//    PixelsTheme {
//        PixelsNavigationBar {
//            items.forEachIndexed { index, item ->
//                PixelsNavigationBarItem(
//                    icon = {
//                        Icon(
//                            imageVector = icons[index],
//                            contentDescription = item,
//                        )
//                    },
//                    selectedIcon = {
//                        Icon(
//                            imageVector = selectedIcons[index],
//                            contentDescription = item,
//                        )
//                    },
//                    label = { Text(item) },
//                    selected = index == 0,
//                    onClick = { },
//                )
//            }
//        }
//    }
//}

//@ThemePreviews
//@Composable
//private fun PixelsNavigationRailPreview() {
//    val items = listOf("Home", "Settings")
//    val icons = listOf(
//        PixelsIcons.home,
//        PixelsIcons.settings,
//    )
//    val selectedIcons = listOf(
//        PixelsIcons.home,
//        PixelsIcons.settings,
//    )
//
//    PixelsTheme {
//        PixelsNavigationRail {
//            items.forEachIndexed { index, item ->
//                PixelsNavigationRailItem(
//                    icon = {
//                        Icon(
//                            imageVector = icons[index],
//                            contentDescription = item,
//                        )
//                    },
//                    selectedIcon = {
//                        Icon(
//                            imageVector = selectedIcons[index],
//                            contentDescription = item,
//                        )
//                    },
//                    label = { Text(item) },
//                    selected = index == 0,
//                    onClick = { },
//                )
//            }
//        }
//    }
//}

private object PixelsNavigationDefaults {
    @Composable
    fun content() = MaterialTheme.colorScheme.onSurface

    @Composable
    fun selectedContent() = MaterialTheme.colorScheme.onPrimary

    @Composable
    fun accent() = MaterialTheme.colorScheme.secondaryContainer
}