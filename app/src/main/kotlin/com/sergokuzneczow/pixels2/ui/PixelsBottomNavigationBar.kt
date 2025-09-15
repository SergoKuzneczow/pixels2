package com.sergokuzneczow.pixels2.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sergokuzneczow.pixels2.navigation.PixelsState
import com.sergokuzneczow.pixels2.navigation.PixelsTopDestinations

@Composable
internal fun PixelsBottomNavigationBar(
    applicationState: PixelsState,
    selectedDestination: MutableState<Int> = remember { mutableIntStateOf(applicationState.startDestination.ordinal) },
    modifier: Modifier = Modifier
) {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets, modifier = modifier) {
        PixelsTopDestinations.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedDestination.value == index,
                onClick = {
                    selectedDestination.value = index
                    applicationState.navigateToTopLevelDestination(destination)
                },
                icon = { Icon(imageVector = destination.icon, contentDescription = null) },
                label = { Text(stringResource(destination.titleTextId)) },
                alwaysShowLabel = false,
            )
        }
    }
}