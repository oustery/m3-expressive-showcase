package com.example.m3expressive.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Guards the hand-written palettes: every text role must stay readable on the role it is paired
 * with, in light and dark, for each brand palette. Dynamic color is provided by the platform and
 * cannot be verified off-device.
 */
class PaletteContrastTest {

    private val brandPalettes = Palette.entries.filter { it != Palette.DYNAMIC }

    @Test
    fun textRolesMeetWcagAa() {
        val failures = mutableListOf<String>()
        for (palette in brandPalettes) {
            for (dark in listOf(false, true)) {
                val scheme = colorSchemeFor(palette, dark)
                for ((name, foreground, background) in pairs(scheme)) {
                    val ratio = contrastRatio(foreground, background)
                    if (ratio < 4.5) {
                        failures += "$palette dark=$dark $name = ${"%.2f".format(ratio)}:1"
                    }
                }
            }
        }
        assertTrue("Roles below 4.5:1\n${failures.joinToString("\n")}", failures.isEmpty())
    }

    @Test
    fun everyPaletteDefinesItsOwnSurfaces() {
        val violet = colorSchemeFor(Palette.VIOLET, dark = false)
        for (palette in brandPalettes - Palette.VIOLET) {
            val surface = colorSchemeFor(palette, dark = false).surfaceContainer
            assertTrue(
                "$palette falls back to the Material 3 baseline surface color",
                surface != violet.surfaceContainer,
            )
        }
        // The Material 3 baseline purple neutrals must never leak into a brand palette.
        val baselineThemedNeutrals = setOf(Color(0xFFF3EDF7), Color(0xFFECE6F0), Color(0xFFE6E0E9))
        for (palette in brandPalettes - Palette.VIOLET) {
            val scheme = colorSchemeFor(palette, dark = false)
            assertTrue(
                "$palette uses a baseline neutral as its section surface",
                scheme.surfaceContainer !in baselineThemedNeutrals,
            )
        }
    }

    private fun pairs(scheme: ColorScheme): List<Triple<String, Color, Color>> = listOf(
        Triple("primary/onPrimary", scheme.onPrimary, scheme.primary),
        Triple("primaryContainer/onPrimaryContainer", scheme.onPrimaryContainer, scheme.primaryContainer),
        Triple("secondary/onSecondary", scheme.onSecondary, scheme.secondary),
        Triple("secondaryContainer/onSecondaryContainer", scheme.onSecondaryContainer, scheme.secondaryContainer),
        Triple("tertiary/onTertiary", scheme.onTertiary, scheme.tertiary),
        Triple("tertiaryContainer/onTertiaryContainer", scheme.onTertiaryContainer, scheme.tertiaryContainer),
        Triple("background/onBackground", scheme.onBackground, scheme.background),
        Triple("surface/onSurface", scheme.onSurface, scheme.surface),
        Triple("surfaceVariant/onSurfaceVariant", scheme.onSurfaceVariant, scheme.surfaceVariant),
        Triple("surfaceContainer/onSurface", scheme.onSurface, scheme.surfaceContainer),
        Triple("surfaceContainerHighest/onSurface", scheme.onSurface, scheme.surfaceContainerHighest),
        Triple("inverseSurface/inverseOnSurface", scheme.inverseOnSurface, scheme.inverseSurface),
    )

    private fun contrastRatio(a: Color, b: Color): Double {
        val first = luminance(a)
        val second = luminance(b)
        return (maxOf(first, second) + 0.05) / (minOf(first, second) + 0.05)
    }

    private fun luminance(color: Color): Double {
        fun channel(value: Float): Double {
            val c = value.toDouble()
            return if (c <= 0.03928) c / 12.92 else ((c + 0.055) / 1.055).pow(2.4)
        }
        return 0.2126 * channel(color.red) + 0.7152 * channel(color.green) + 0.0722 * channel(color.blue)
    }
}
