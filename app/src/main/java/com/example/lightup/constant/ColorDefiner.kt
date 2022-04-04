package com.example.lightup.constant

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ColorDefiner(): Color {
    return if (isSystemInDarkTheme()) {
        Color.LightGray
    } else {
        Color.White
    }
}
