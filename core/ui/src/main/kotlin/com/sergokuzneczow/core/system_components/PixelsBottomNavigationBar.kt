package com.sergokuzneczow.core.system_components

//@Composable
//internal fun PixelsBottomNavigationBar(
//    applicationState: PixelsState,
//    selectedDestination: MutableState<Int> = remember { mutableIntStateOf(applicationState.startDestination.ordinal) },
//    modifier: Modifier = Modifier
//) {
//    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets, modifier = modifier) {
//        PixelsTopDestinations.entries.forEachIndexed { index, destination ->
//            NavigationBarItem(
//                selected = selectedDestination.value == index,
//                onClick = {
//                    selectedDestination.value = index
//                    applicationState.navigateToTopLevelDestination(destination)
//                },
//                icon = { Icon(imageVector = destination.icon, contentDescription = null) },
//                label = { Text(stringResource(destination.titleTextId)) },
//                alwaysShowLabel = false,
//            )
//        }
//    }
//}