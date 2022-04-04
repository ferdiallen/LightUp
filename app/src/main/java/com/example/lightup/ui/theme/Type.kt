package com.example.lightup.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lightup.fonts.FontsCustom

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontsCustom.fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = FontsCustom.fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FontsCustom.fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = FontsCustom.fonts,
        fontWeight = FontWeight.Thin,
        fontSize = 16.sp
    ),
    h3 = TextStyle(
        fontFamily = FontsCustom.fonts,
        fontStyle = FontStyle.Italic,fontSize = 16.sp
    )
)