package com.sergokuzneczow.utilities

import androidx.navigation.NavOptions
import androidx.navigation.navOptions

public inline fun <reified ROUTE : Any> lastItemInclusiveBackstack(): NavOptions = navOptions {
    popUpTo<ROUTE> {
        saveState = true
        inclusive = true
    }
    launchSingleTop = true
    restoreState = true
}

public inline fun <reified ROUTE : Any> lastItemBackstack(): NavOptions = navOptions {
    popUpTo<ROUTE> {
        saveState = true
        inclusive = false
    }
    launchSingleTop = false
    restoreState = true
}