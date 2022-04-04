package com.example.lightup.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.lightup.R

object FontsCustom {
    val fonts = FontFamily(
        Font(R.font.montserrat_black, FontWeight.Black),
        Font(R.font.montserrat_blackitalic, FontWeight.Black, FontStyle.Italic),
        Font(R.font.montserrat_bold, weight = FontWeight.Bold),
        Font(R.font.montserrat_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
        Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
        Font(R.font.montserrat_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(R.font.montserrat_extralight, FontWeight.ExtraLight),
        Font(R.font.montserrat_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
        Font(R.font.montserrat_italic, style = FontStyle.Italic),
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_lightitalic, FontWeight.Light, FontStyle.Italic),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_mediumitalic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold),
        Font(R.font.montserrat_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
        Font(R.font.montserrat_thin, FontWeight.Thin),
        Font(R.font.montserrat_thinitalic, FontWeight.Thin, FontStyle.Italic)
    )
}