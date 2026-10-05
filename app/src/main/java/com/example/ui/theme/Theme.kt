package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BerserkDarkColorScheme = darkColorScheme(
    primary = BerserkBloodRed,
    onPrimary = Color.White,
    primaryContainer = BerserkDeepBlood,
    onPrimaryContainer = Color(0xFFFFEBEE),
    secondary = BerserkCrimsonGlow,
    onSecondary = Color.Black,
    secondaryContainer = BerserkSurfaceVariant,
    onSecondaryContainer = BerserkSteel,
    tertiary = BerserkBehelitGold,
    onTertiary = Color.Black,
    background = BerserkObsidian,
    onBackground = BerserkSteel,
    surface = BerserkDarkSurface,
    onSurface = BerserkSteel,
    surfaceVariant = BerserkSurfaceVariant,
    onSurfaceVariant = BerserkSteelDim,
    outline = BerserkGlassBorder,
    outlineVariant = BerserkSteelDark
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BerserkDarkColorScheme,
        typography = Typography,
        content = content
    )
}
