package com.example.m3expressive.ui.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.m3expressive.theme.Palette
import com.example.m3expressive.theme.ThemeMode
import com.example.m3expressive.ui.components.Caption
import com.example.m3expressive.ui.components.ColorDot
import com.example.m3expressive.ui.components.ScreenIntro
import com.example.m3expressive.ui.components.ShowcaseScreen
import com.example.m3expressive.ui.components.ShowcaseSection

@Composable
fun ThemeScreen(
    mode: ThemeMode,
    palette: Palette,
    onModeChange: (ThemeMode) -> Unit,
    onPaletteChange: (Palette) -> Unit,
) {
    val dynamicColorSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    ShowcaseScreen {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                ScreenIntro(
                    "Make it yours",
                    "One system.\nMany voices.",
                    "Switch the entire experience instantly. Choices survive process recreation.",
                )
            }
            item {
                ShowcaseSection("01", "Appearance", "Light, dark, or follow the device") {
                    SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth().selectableGroup()) {
                        ThemeMode.entries.forEachIndexed { index, item ->
                            SegmentedButton(
                                selected = mode == item,
                                onClick = { onModeChange(item) },
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = ThemeMode.entries.size),
                            ) { Text(item.label) }
                        }
                    }
                }
            }
            item {
                ShowcaseSection("02", "Color stories", "Vibrant accents, harmonious containers") {
                    // selectableGroup + Role.RadioButton let TalkBack announce the choice and its
                    // current selection instead of reading a plain, unlabeled row.
                    Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Palette.entries.forEach { item ->
                            PaletteRow(
                                item = item,
                                selected = palette == item,
                                enabled = item != Palette.DYNAMIC || dynamicColorSupported,
                                onSelect = onPaletteChange,
                            )
                        }
                    }
                    if (!dynamicColorSupported) {
                        Caption("Dynamic color needs Android 12+. This device keeps the Violet palette.")
                    }
                }
            }
            item { CurrentPalette() }
            item { TypographySpecimen() }
            item { ShapeSpecimen() }
        }
    }
}

@Composable
private fun PaletteRow(item: Palette, selected: Boolean, enabled: Boolean, onSelect: (Palette) -> Unit) {
    val accents = when (item) {
        Palette.VIOLET -> listOf(Color(0xFF6750A4), Color(0xFFB3265E), Color(0xFFEADDFF))
        Palette.SUNSET -> listOf(Color(0xFFAD2F51), Color(0xFF875100), Color(0xFFFFD9DF))
        Palette.OCEAN -> listOf(Color(0xFF006874), Color(0xFF535E7D), Color(0xFF9EEFFD))
        Palette.DYNAMIC -> listOf(Color(0xFF4C662B), Color(0xFF006874), Color(0xFFAD2F51))
    }
    Row(
        Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium)
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
            .border(
                1.dp,
                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                MaterialTheme.shapes.medium,
            )
            .selectable(selected = selected, enabled = enabled, role = Role.RadioButton, onClick = { onSelect(item) })
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
            accents.forEach { color ->
                ColorDot(
                    color = color,
                    modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                    size = 28.dp,
                )
            }
        }
        Text(
            item.label,
            Modifier.weight(1f).padding(start = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        if (selected) Icon(Icons.Default.Check, "Selected", tint = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun CurrentPalette() {
    ShowcaseSection("03", "Live tonal palette", "Roles, not isolated hex values") {
        val colors = MaterialTheme.colorScheme
        listOf(
            "Primary" to colors.primary,
            "On primary" to colors.onPrimary,
            "Primary container" to colors.primaryContainer,
            "Tertiary" to colors.tertiary,
            "Tertiary container" to colors.tertiaryContainer,
            "Surface container" to colors.surfaceContainer,
        ).chunked(3).forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { (label, color) ->
                    Column(
                        Modifier.weight(1f).semantics(mergeDescendants = true) { },
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Box(Modifier.fillMaxWidth().height(56.dp).clip(MaterialTheme.shapes.small).background(color))
                        Text(
                            label,
                            style = MaterialTheme.typography.labelSmall,
                            // Long role names need two lines next to three swatches; one line with
                            // no ellipsis clipped them at larger font scales.
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TypographySpecimen() {
    ShowcaseSection("04", "Editorial type", "Weight and scale create hierarchy") {
        Text("Aa", style = MaterialTheme.typography.displayLarge, color = MaterialTheme.colorScheme.primary)
        Text(
            "Make it bold.",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.semantics { heading() },
        )
        Text(
            "Then give the supporting copy room to breathe. Expressive typography balances character with clarity.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text("DISPLAY / HEADLINE / BODY / LABEL", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun ShapeSpecimen() {
    ShowcaseSection("05", "Shape contrast", "Softness meets a precise edge") {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                Modifier.weight(1f).height(90.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center,
            ) {
                Text("4 dp", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.labelLarge)
            }
            Box(
                Modifier.weight(1f).height(90.dp).clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center,
            ) {
                Text("20 dp", color = MaterialTheme.colorScheme.onTertiary, style = MaterialTheme.typography.labelLarge)
            }
            Box(
                Modifier.weight(1f).height(90.dp).clip(RoundedCornerShape(48.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text("48 dp", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
