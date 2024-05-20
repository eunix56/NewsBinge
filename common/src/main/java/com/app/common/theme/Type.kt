package com.app.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 24.sp,
        color = Black400,
        fontFamily = avenirFontsFamily,
        fontWeight = FontWeight.Black,
        fontSynthesis = FontSynthesis.Weight
    ),

    titleLarge = TextStyle(
        fontSize = 30.sp,
        color = Black400,
        fontFamily = avenirFontsFamily,
        fontWeight = FontWeight.Black,
        fontSynthesis = FontSynthesis.Weight
    ),

    titleMedium = TextStyle(
        fontSize = 16.sp,
        color = Black400,
        fontFamily = avenirFontsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSynthesis = FontSynthesis.Weight
    ),

    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = avenirFontsFamily,
        fontWeight = FontWeight.Medium,
        fontSynthesis = FontSynthesis.Weight
    ),

    labelMedium = TextStyle(
        fontSize = 12.sp,
        color = Grey200,
        fontFamily = avenirFontsFamily,
        fontWeight = FontWeight.Medium,
        fontSynthesis = FontSynthesis.Weight
    ),

    labelSmall = TextStyle(
        fontSize = 12.sp,
        color = Grey200,
        fontFamily = avenirFontsFamily,
        fontWeight = FontWeight.Normal,
        fontSynthesis = FontSynthesis.Weight
    ),

    bodyMedium = TextStyle(
        fontSize = 14.sp,
        color = Grey200,
        fontFamily = avenirFontsFamily,
        fontWeight = FontWeight.Normal,
        fontSynthesis = FontSynthesis.Weight
    ),

    headlineMedium = TextStyle(
        fontSize = 15.sp,
        color = Black400,
        fontFamily = avenirFontsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSynthesis = FontSynthesis.Weight
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)