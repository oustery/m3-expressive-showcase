package com.example.m3expressive.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

enum class ThemeMode(val label: String) { SYSTEM("System"), LIGHT("Light"), DARK("Dark") }
enum class Palette(val label: String) { VIOLET("Violet"), SUNSET("Sunset"), OCEAN("Ocean"), DYNAMIC("Dynamic") }

private val VioletLight = lightColorScheme(
    primary = Color(0xFF6750A4), onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF), onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF625B71), secondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFFB3265E), onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD8E6), onTertiaryContainer = Color(0xFF3E001D),
    background = Color(0xFFFFFBFE), surface = Color(0xFFFFFBFE),
    surfaceContainer = Color(0xFFF3EDF7), surfaceContainerHigh = Color(0xFFECE6F0),
)
private val VioletDark = darkColorScheme(
    primary = Color(0xFFD0BCFF), onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B), onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC), secondaryContainer = Color(0xFF4A4458),
    tertiary = Color(0xFFFFB0CC), onTertiary = Color(0xFF650033),
    tertiaryContainer = Color(0xFF8E0046), onTertiaryContainer = Color(0xFFFFD8E6),
)
private val SunsetLight = lightColorScheme(
    primary = Color(0xFFAD2F51), onPrimary = Color.White,
    primaryContainer = Color(0xFFFFD9DF), onPrimaryContainer = Color(0xFF400015),
    secondary = Color(0xFF75565B), secondaryContainer = Color(0xFFFFD9DF),
    tertiary = Color(0xFF875100), onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDDB8), onTertiaryContainer = Color(0xFF2B1700),
    background = Color(0xFFFFF8F7), surface = Color(0xFFFFF8F7),
)
private val SunsetDark = darkColorScheme(
    primary = Color(0xFFFFB1C1), onPrimary = Color(0xFF660025),
    primaryContainer = Color(0xFF8D143B), onPrimaryContainer = Color(0xFFFFD9DF),
    tertiary = Color(0xFFFFB95E), tertiaryContainer = Color(0xFF663C00),
)
private val OceanLight = lightColorScheme(
    primary = Color(0xFF006874), onPrimary = Color.White,
    primaryContainer = Color(0xFF9EEFFD), onPrimaryContainer = Color(0xFF001F24),
    secondary = Color(0xFF4A6267), secondaryContainer = Color(0xFFCDE7EC),
    tertiary = Color(0xFF535E7D), onTertiary = Color.White,
    tertiaryContainer = Color(0xFFDAE2FF), onTertiaryContainer = Color(0xFF0F1A37),
    background = Color(0xFFF5FAFB), surface = Color(0xFFF5FAFB),
)
private val OceanDark = darkColorScheme(
    primary = Color(0xFF82D3E0), onPrimary = Color(0xFF00363D),
    primaryContainer = Color(0xFF004F58), onPrimaryContainer = Color(0xFF9EEFFD),
    tertiary = Color(0xFFBAC6EA), tertiaryContainer = Color(0xFF3B4665),
)

private val ExpressiveTypography = Typography(
    displayLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Black, fontSize = 57.sp, lineHeight = 60.sp, letterSpacing = (-1.5).sp),
    displayMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.ExtraBold, fontSize = 45.sp, lineHeight = 48.sp, letterSpacing = (-1).sp),
    displaySmall = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 36.sp, lineHeight = 40.sp, letterSpacing = (-0.5).sp),
    headlineLarge = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 32.sp, lineHeight = 38.sp, letterSpacing = (-0.5).sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 34.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 22.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp, lineHeight = 20.sp),
)

private val ExpressiveShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(32.dp),
    extraLarge = RoundedCornerShape(48.dp),
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppExpressiveTheme(
    mode: ThemeMode = ThemeMode.SYSTEM,
    palette: Palette = Palette.VIOLET,
    content: @Composable () -> Unit,
) {
    val dark = when (mode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
    val context = LocalContext.current
    val colors = when (palette) {
        Palette.VIOLET -> if (dark) VioletDark else VioletLight
        Palette.SUNSET -> if (dark) SunsetDark else SunsetLight
        Palette.OCEAN -> if (dark) OceanDark else OceanLight
        Palette.DYNAMIC -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (dark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } else if (dark) VioletDark else VioletLight
    }
    MaterialExpressiveTheme(
        colorScheme = colors,
        typography = ExpressiveTypography,
        shapes = ExpressiveShapes,
        motionScheme = MotionScheme.expressive(),
        content = content,
    )
}
