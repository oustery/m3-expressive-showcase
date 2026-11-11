package com.example.m3expressive.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.m3expressive.theme.AppExpressiveTheme
import com.example.m3expressive.theme.LocalIsDarkTheme
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
        SyncSystemBarIcons()

        var selectedTab by rememberSaveable { mutableStateOf(0) }
        // Keeps each destination's saved state, including its list scroll position, so switching
        // tabs does not throw the user back to the top of the page they were reading.
        val stateHolder = rememberSaveableStateHolder()
        val snackbar = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        // One message at a time: without dismissing the visible snackbar a burst of taps queues
        // several seconds of stale messages instead of confirming the latest action.
        val notify: (String) -> Unit = { message ->
            scope.launch {
                snackbar.currentSnackbarData?.dismiss()
                snackbar.showSnackbar(message)
            }
        }
        val isDark = LocalIsDarkTheme.current

        // Back should walk the tab stack before it leaves the app.
        BackHandler(enabled = selectedTab != 0) { selectedTab = 0 }

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("M3 / EXPRESSIVE", style = MaterialTheme.typography.titleMedium) },
                        actions = {
                            IconButton(onClick = { themeModeName = if (isDark) ThemeMode.LIGHT.name else ThemeMode.DARK.name }) {
                                Icon(
                                    if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = if (isDark) "Switch to light theme" else "Switch to dark theme",
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
                    transitionSpec = {
                        fadeIn(tween(durationMillis = 180)) togetherWith fadeOut(tween(durationMillis = 90))
                    },
                    label = "Destination transition",
                    modifier = Modifier.padding(padding),
                ) { tab ->
                    stateHolder.SaveableStateProvider(tab) {
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
}

/**
 * Edge-to-edge is enabled on the activity, so the status and navigation bar icon colors are owned
 * by the window. They follow the in-app appearance choice rather than the system one — otherwise
 * picking Dark on a light device leaves dark icons on a dark background.
 */
@Composable
private fun SyncSystemBarIcons() {
    val view = LocalView.current
    val isDark = LocalIsDarkTheme.current
    val activity = remember(view) { view.context.findActivity() }
    if (activity != null && !view.isInEditMode) {
        SideEffect {
            WindowCompat.getInsetsController(activity.window, view).apply {
                isAppearanceLightStatusBars = !isDark
                isAppearanceLightNavigationBars = !isDark
            }
        }
    }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
