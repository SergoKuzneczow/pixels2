package com.sergokuzneczow.utilities

import androidx.navigation.NavOptions
import androidx.navigation.navOptions

public inline fun <reified ROUTE : Any> excludeBackstack(): NavOptions = navOptions {
    popUpTo<ROUTE> {
        saveState = true
        inclusive = true
    }
    launchSingleTop = true
    restoreState = true
}