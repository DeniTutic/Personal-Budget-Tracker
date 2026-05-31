package com.example.mobilno_projekat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography().copy(
    headlineLarge = Typography().headlineLarge.copy(
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleLarge = Typography().titleLarge.copy(
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = Typography().titleMedium.copy(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
)