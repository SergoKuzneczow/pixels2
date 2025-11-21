package com.sergokuzneczow.pixels2.utilities

import androidx.activity.compose.ManagedActivityResultLauncher

internal class PermissionRequest(
    private val permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
) {

    internal companion object {
        var isGranted: (() -> Unit)? = null
        var isNotGranted: (() -> Unit)? = null
    }

    fun launch(
        permission: String,
        isPermissionGranted: (() -> Unit)? = null,
        isPermissionNotGranter: (() -> Unit)? = null,
    ) {
        isGranted = isPermissionGranted
        isNotGranted = isPermissionNotGranter
        permissionLauncher.launch(permission)
    }
}