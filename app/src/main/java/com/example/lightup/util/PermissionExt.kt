package com.example.lightup.util

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@ExperimentalPermissionsApi
fun PermissionState.isPermanentDeclined(): Boolean {
    return !status.isGranted && !status.shouldShowRationale
}