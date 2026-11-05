package com.example.m3expressive.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.m3expressive.theme.AppExpressiveTheme
import com.example.m3expressive.theme.Palette
import com.example.m3expressive.theme.ThemeMode
import com.example.m3expressive.ui.screens.ComponentsScreen
import com.example.m3expressive.ui.screens.HomeScreen
import com.example.m3expressive.ui.screens.MotionScreen
import com.example.m3expressive.ui.screens.ThemeScreen
import kotlinx.coroutines.launch

private data class Destination(val label: String, val icon: ImageVector)
private val destinations = listOf(
    Destination("Discover", Icons.Default.Home),
    Destination("Components", Icons.Default.Category),
    Destination("Motion", Icons.Default.AutoAwesome),
    Destination("Color", Icons.Default.Palette),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowcaseApp() {
    var themeModeName by rememberSaveable { mutableStateOf(ThemeMode.SYSTEM.name) }
    var paletteName by rememberSaveable { mutableStateOf(Palette.VIOLET.name) }
    val themeMode = ThemeMode.valueOf(themeModeName)
    val palette = Palette.valueOf(paletteName)

    AppExpressiveTheme(themeMode, palette) {
        var selectedTab by rememberSaveable { mutableIntStateOf(0) }
        val snackbar = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val notify: (String) -> Unit = { message -> scope.launch { snackbar.showSnackbar(message) } }

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("M3 / EXPRESSIVE", style = MaterialTheme.typography.titleMedium) },
                        actions = {
                            IconButton(onClick = {
                                themeModeName = if (themeMode == ThemeMode.DARK) ThemeMode.LIGHT.name else ThemeMode.DARK.name
                            }) {
                                Icon(
                                    if (themeMode == ThemeMode.DARK) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = "Toggle dark theme",
                                )
                            }
                        },
                    )
                },
                bottomBar = {
                    NavigationBar {
                        destinations.forEachIndexed { index, destination ->
                            NavigationBarItem(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                icon = { Icon(destination.icon, contentDescription = null) },
                                label = { Text(destination.label) },
                            )
                        }
                    }
                },
                snackbarHost = { SnackbarHost(snackbar) },
            ) { padding ->
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "Destination transition",
                    modifier = Modifier.padding(padding),
                ) { tab ->
                    when (tab) {
                        0 -> HomeScreen(onExplore = { selectedTab = 1 }, onNotify = notify)
                        1 -> ComponentsScreen(onNotify = notify)
                        2 -> MotionScreen()
                        else -> ThemeScreen(
                            mode = themeMode,
                            palette = palette,
                            onModeChange = { themeModeName = it.name },
                            onPaletteChange = { paletteName = it.name },
                        )
                    }
                }
            }
        }
    }
}
