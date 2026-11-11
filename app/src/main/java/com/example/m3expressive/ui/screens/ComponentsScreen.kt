package com.example.m3expressive.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButtonShapes
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.m3expressive.ui.components.Caption
import com.example.m3expressive.ui.components.ScreenIntro
import com.example.m3expressive.ui.components.ShowcaseScreen
import com.example.m3expressive.ui.components.ShowcaseSection

@Composable
fun ComponentsScreen(onNotify: (String) -> Unit) {
    ShowcaseScreen {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                ScreenIntro(
                    "Component library",
                    "Familiar.\nBut more alive.",
                    "Interact with the new expressive building blocks. Every control is functional.",
                )
            }
            item { WavyProgressExample() }
            item { SplitActionExample(onNotify) }
            item { ConnectedActionsExample(onNotify) }
            item { MorphingIndicatorsExample() }
            item { SelectionExample() }
            item { FloatingToolbarExample(onNotify) }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun WavyProgressExample() {
    // rememberSaveable, not remember: list items are disposed when scrolled out of view, and the
    // progress would otherwise reset every time the section came back on screen.
    var progress by rememberSaveable { mutableStateOf(.64f) }
    ShowcaseSection("01", "Wavy progress", "A little rhythm for every wait") {
        Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)
        LinearWavyProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth(), wavelength = 28.dp)
        Slider(value = progress, onValueChange = { progress = it })
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            CircularWavyProgressIndicator(progress = { progress }, modifier = Modifier.size(64.dp))
            Column {
                Text("Determinate progress", style = MaterialTheme.typography.titleMedium)
                Caption("Drag the slider to change both indicators")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SplitActionExample(onNotify: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var format by rememberSaveable { mutableStateOf("APK") }
    ShowcaseSection("02", "Split action", "One decision, two connected controls") {
        SplitButtonLayout(
            leadingButton = {
                SplitButtonDefaults.LeadingButton(onClick = { onNotify("Preparing $format download…") }) {
                    Icon(Icons.Default.Download, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Download $format")
                }
            },
            trailingButton = {
                // The menu is anchored to the trailing half so it opens under the chevron.
                Box {
                    SplitButtonDefaults.TrailingButton(
                        onClick = { expanded = true },
                        colors = ButtonDefaults.filledTonalButtonColors(),
                    ) {
                        Icon(Icons.Default.ArrowDropDown, "Choose format")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        listOf("APK", "AAB", "Source").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                leadingIcon = { if (format == option) Icon(Icons.Default.Check, null) },
                                onClick = { format = option; expanded = false },
                            )
                        }
                    }
                }
            },
        )
        Caption("The menu changes the primary action without breaking visual continuity.")
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ConnectedActionsExample(onNotify: (String) -> Unit) {
    var favorite by rememberSaveable { mutableStateOf(false) }
    var bookmark by rememberSaveable { mutableStateOf(false) }
    ShowcaseSection("03", "Connected actions", "Neighbours that share one shape") {
        Row(horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)) {
            FilledTonalIconToggleButton(
                checked = favorite,
                onCheckedChange = { favorite = it },
                shapes = connectedLeadingIconShapes(),
            ) {
                Icon(
                    if (favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    if (favorite) "Remove from favorites" else "Add to favorites",
                )
            }
            FilledTonalIconToggleButton(
                checked = bookmark,
                onCheckedChange = { bookmark = it },
                shapes = connectedTrailingIconShapes(),
            ) {
                Icon(
                    if (bookmark) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    if (bookmark) "Remove bookmark" else "Add bookmark",
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { onNotify("Shared with your team") }) {
                Icon(Icons.Default.Share, null, Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("Share")
            }
            OutlinedButton(onClick = { onNotify("Editor opened") }) {
                Icon(Icons.Default.Edit, null, Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("Edit")
            }
        }
        Caption("Toggle either icon: the pair morphs as one group while the actions stay separate.")
    }
}

/** Connected-group shapes for an icon toggle, mirroring [ButtonGroupDefaults] group tokens. */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun connectedLeadingIconShapes(): IconToggleButtonShapes = IconToggleButtonShapes(
    shape = ButtonGroupDefaults.connectedLeadingButtonShape,
    pressedShape = ButtonGroupDefaults.connectedLeadingButtonPressShape,
    checkedShape = ButtonGroupDefaults.connectedButtonCheckedShape,
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun connectedTrailingIconShapes(): IconToggleButtonShapes = IconToggleButtonShapes(
    shape = ButtonGroupDefaults.connectedTrailingButtonShape,
    pressedShape = ButtonGroupDefaults.connectedTrailingButtonPressShape,
    checkedShape = ButtonGroupDefaults.connectedButtonCheckedShape,
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun MorphingIndicatorsExample() {
    ShowcaseSection("04", "Morphing indicators", "Loading becomes a moment of delight") {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LoadingIndicator(Modifier.size(64.dp))
                Spacer(Modifier.height(8.dp))
                Caption("Morphing")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularWavyProgressIndicator(Modifier.size(64.dp))
                Spacer(Modifier.height(8.dp))
                Caption("Wavy")
            }
        }
    }
}

@Composable
private fun SelectionExample() {
    var updates by rememberSaveable { mutableStateOf(true) }
    var accepted by rememberSaveable { mutableStateOf(true) }
    ShowcaseSection("05", "Selection controls", "Accessible states with a bold voice") {
        // The whole row toggles: a switch or checkbox alone is a small target and leaves the
        // label dead to touch.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(value = updates, onValueChange = { updates = it }, role = Role.Switch)
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text("Expressive updates", style = MaterialTheme.typography.titleMedium)
                Caption("New components and releases")
            }
            Spacer(Modifier.width(12.dp))
            Switch(checked = updates, onCheckedChange = null)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(value = accepted, onValueChange = { accepted = it }, role = Role.Checkbox)
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = accepted, onCheckedChange = null)
            Spacer(Modifier.width(12.dp))
            Text("Use springs responsibly", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FloatingToolbarExample(onNotify: (String) -> Unit) {
    ShowcaseSection("06", "Floating toolbar", "Actions that stay close, not heavy") {
        HorizontalFloatingToolbar(
            expanded = true,
            floatingActionButton = {
                FloatingActionButton(onClick = { onNotify("New creation started") }) {
                    Icon(Icons.Default.Add, "Create")
                }
            },
        ) {
            IconButton(onClick = { onNotify("Favorite added") }) { Icon(Icons.Default.FavoriteBorder, "Favorite") }
            IconButton(onClick = { onNotify("Shared") }) { Icon(Icons.Default.Share, "Share") }
            IconButton(onClick = { onNotify("More actions") }) { Icon(Icons.Default.MoreHoriz, "More") }
        }
    }
}
