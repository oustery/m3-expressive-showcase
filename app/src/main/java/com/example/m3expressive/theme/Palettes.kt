package com.example.m3expressive.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/** How the app decides between the light and dark color schemes. */
enum class ThemeMode(val label: String) { SYSTEM("System"), LIGHT("Light"), DARK("Dark") }

/** The color stories the showcase ships with. */
enum class Palette(val label: String) {
    VIOLET("Violet"),
    SUNSET("Sunset"),
    OCEAN("Ocean"),
    DYNAMIC("Dynamic"),
}

/**
 * Returns the brand color scheme for [palette].
 *
 * Every role is defined explicitly: leaving a role out makes it fall back to the Material 3
 * baseline (a purple-leaning neutral), which visibly breaks the Sunset and Ocean palettes.
 * [Palette.DYNAMIC] falls back to the Violet brand palette — [AppExpressiveTheme] swaps in the
 * platform wallpaper colors on Android 12 and newer.
 */
fun colorSchemeFor(palette: Palette, dark: Boolean): ColorScheme = when (palette) {
    Palette.VIOLET -> if (dark) VioletDark else VioletLight
    Palette.SUNSET -> if (dark) SunsetDark else SunsetLight
    Palette.OCEAN -> if (dark) OceanDark else OceanLight
    Palette.DYNAMIC -> if (dark) VioletDark else VioletLight
}

private val VioletLight = lightColorScheme(
    primary = Color(0xFF6750A4), onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEADDFF), onPrimaryContainer = Color(0xFF21005D),
    inversePrimary = Color(0xFFD0BCFF),
    secondary = Color(0xFF625B71), onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE8DEF8), onSecondaryContainer = Color(0xFF1D192B),
    tertiary = Color(0xFFB3265E), onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD8E6), onTertiaryContainer = Color(0xFF3E001D),
    background = Color(0xFFFFFBFE), onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE), onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC), onSurfaceVariant = Color(0xFF49454F),
    inverseSurface = Color(0xFF313033), inverseOnSurface = Color(0xFFF4EFF4),
    outline = Color(0xFF79747E), outlineVariant = Color(0xFFCAC4D0),
    surfaceContainerLowest = Color(0xFFFFFFFF), surfaceContainerLow = Color(0xFFF7F2FA),
    surfaceContainer = Color(0xFFF3EDF7), surfaceContainerHigh = Color(0xFFECE6F0),
    surfaceContainerHighest = Color(0xFFE6E0E9),
    surfaceDim = Color(0xFFDED8E1), surfaceBright = Color(0xFFFFFBFE),
)

private val VioletDark = darkColorScheme(
    primary = Color(0xFFD0BCFF), onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B), onPrimaryContainer = Color(0xFFEADDFF),
    inversePrimary = Color(0xFF6750A4),
    secondary = Color(0xFFCCC2DC), onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458), onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFFFB0CC), onTertiary = Color(0xFF650033),
    tertiaryContainer = Color(0xFF8E0046), onTertiaryContainer = Color(0xFFFFD8E6),
    background = Color(0xFF141218), onBackground = Color(0xFFE6E0E9),
    surface = Color(0xFF141218), onSurface = Color(0xFFE6E0E9),
    surfaceVariant = Color(0xFF49454F), onSurfaceVariant = Color(0xFFCAC4D0),
    inverseSurface = Color(0xFFE6E0E9), inverseOnSurface = Color(0xFF313033),
    outline = Color(0xFF938F99), outlineVariant = Color(0xFF49454F),
    surfaceContainerLowest = Color(0xFF0F0D13), surfaceContainerLow = Color(0xFF1D1B20),
    surfaceContainer = Color(0xFF211F26), surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainerHighest = Color(0xFF36343B),
    surfaceDim = Color(0xFF141218), surfaceBright = Color(0xFF3B383E),
)

private val SunsetLight = lightColorScheme(
    primary = Color(0xFFAD2F51), onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFD9DF), onPrimaryContainer = Color(0xFF400015),
    inversePrimary = Color(0xFFFFB1C1),
    secondary = Color(0xFF75565B), onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFD9DF), onSecondaryContainer = Color(0xFF2B151A),
    tertiary = Color(0xFF875100), onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDDB8), onTertiaryContainer = Color(0xFF2B1700),
    background = Color(0xFFFFF8F7), onBackground = Color(0xFF201A1A),
    surface = Color(0xFFFFF8F7), onSurface = Color(0xFF201A1A),
    surfaceVariant = Color(0xFFF2DEE0), onSurfaceVariant = Color(0xFF524345),
    inverseSurface = Color(0xFF362F2F), inverseOnSurface = Color(0xFFFBEDEC),
    outline = Color(0xFF847375), outlineVariant = Color(0xFFD6C2C3),
    surfaceContainerLowest = Color(0xFFFFFFFF), surfaceContainerLow = Color(0xFFFEF1F1),
    surfaceContainer = Color(0xFFFAEBEB), surfaceContainerHigh = Color(0xFFF4E5E5),
    surfaceContainerHighest = Color(0xFFEEE0E0),
    surfaceDim = Color(0xFFE8D6D6), surfaceBright = Color(0xFFFFF8F7),
)

private val SunsetDark = darkColorScheme(
    primary = Color(0xFFFFB1C1), onPrimary = Color(0xFF660025),
    primaryContainer = Color(0xFF8D143B), onPrimaryContainer = Color(0xFFFFD9DF),
    inversePrimary = Color(0xFFAD2F51),
    secondary = Color(0xFFE4BDC2), onSecondary = Color(0xFF422A2F),
    secondaryContainer = Color(0xFF5B4045), onSecondaryContainer = Color(0xFFFFD9DF),
    tertiary = Color(0xFFFFB95E), onTertiary = Color(0xFF482900),
    tertiaryContainer = Color(0xFF663C00), onTertiaryContainer = Color(0xFFFFDDB8),
    background = Color(0xFF1A1112), onBackground = Color(0xFFF0DEDE),
    surface = Color(0xFF1A1112), onSurface = Color(0xFFF0DEDE),
    surfaceVariant = Color(0xFF524345), onSurfaceVariant = Color(0xFFD6C2C3),
    inverseSurface = Color(0xFFF0DEDE), inverseOnSurface = Color(0xFF362F2F),
    outline = Color(0xFF9F8C8E), outlineVariant = Color(0xFF524345),
    surfaceContainerLowest = Color(0xFF140C0D), surfaceContainerLow = Color(0xFF22191A),
    surfaceContainer = Color(0xFF271D1E), surfaceContainerHigh = Color(0xFF322829),
    surfaceContainerHighest = Color(0xFF3D3233),
    surfaceDim = Color(0xFF1A1112), surfaceBright = Color(0xFF423738),
)

private val OceanLight = lightColorScheme(
    primary = Color(0xFF006874), onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF9EEFFD), onPrimaryContainer = Color(0xFF001F24),
    inversePrimary = Color(0xFF82D3E0),
    secondary = Color(0xFF4A6267), onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCDE7EC), onSecondaryContainer = Color(0xFF051F23),
    tertiary = Color(0xFF535E7D), onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDAE2FF), onTertiaryContainer = Color(0xFF0F1A37),
    background = Color(0xFFF5FAFB), onBackground = Color(0xFF171D1E),
    surface = Color(0xFFF5FAFB), onSurface = Color(0xFF171D1E),
    surfaceVariant = Color(0xFFDAE4E6), onSurfaceVariant = Color(0xFF3F484A),
    inverseSurface = Color(0xFF2B3132), inverseOnSurface = Color(0xFFECF2F2),
    outline = Color(0xFF6F797B), outlineVariant = Color(0xFFBFC8CA),
    surfaceContainerLowest = Color(0xFFFFFFFF), surfaceContainerLow = Color(0xFFEFF4F5),
    surfaceContainer = Color(0xFFE9EFF0), surfaceContainerHigh = Color(0xFFE3E9EA),
    surfaceContainerHighest = Color(0xFFDDE3E4),
    surfaceDim = Color(0xFFD5DBDC), surfaceBright = Color(0xFFF5FAFB),
)

private val OceanDark = darkColorScheme(
    primary = Color(0xFF82D3E0), onPrimary = Color(0xFF00363D),
    primaryContainer = Color(0xFF004F58), onPrimaryContainer = Color(0xFF9EEFFD),
    inversePrimary = Color(0xFF006874),
    secondary = Color(0xFFB1CBD0), onSecondary = Color(0xFF1C3438),
    secondaryContainer = Color(0xFF334B4F), onSecondaryContainer = Color(0xFFCDE7EC),
    tertiary = Color(0xFFBAC6EA), onTertiary = Color(0xFF25304C),
    tertiaryContainer = Color(0xFF3B4665), onTertiaryContainer = Color(0xFFDAE2FF),
    background = Color(0xFF0E1415), onBackground = Color(0xFFDDE3E4),
    surface = Color(0xFF0E1415), onSurface = Color(0xFFDDE3E4),
    surfaceVariant = Color(0xFF3F484A), onSurfaceVariant = Color(0xFFBFC8CA),
    inverseSurface = Color(0xFFDDE3E4), inverseOnSurface = Color(0xFF2B3132),
    outline = Color(0xFF899395), outlineVariant = Color(0xFF3F484A),
    surfaceContainerLowest = Color(0xFF090F10), surfaceContainerLow = Color(0xFF171D1E),
    surfaceContainer = Color(0xFF1B2122), surfaceContainerHigh = Color(0xFF252B2C),
    surfaceContainerHighest = Color(0xFF303637),
    surfaceDim = Color(0xFF0E1415), surfaceBright = Color(0xFF343A3B),
)
