package com.example.m3expressive.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.example.m3expressive.ui.components.Caption
import com.example.m3expressive.ui.components.ScreenIntro
import com.example.m3expressive.ui.components.ShowcaseScreen
import com.example.m3expressive.ui.components.ShowcaseSection

@Composable
fun MotionScreen() {
    ShowcaseScreen {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                ScreenIntro(
                    "Physics, not keyframes",
                    "Motion with\npersonality.",
                    "Springs preserve velocity and respond naturally when a goal changes mid-animation.",
                )
            }
            item { ShapeMorphExample() }
            item { SpringPlayground() }
            item { MotionPrinciples() }
        }
    }
}

@Composable
private fun ShapeMorphExample() {
    var selected by rememberSaveable { mutableStateOf(false) }
    val corner by animateDpAsState(
        targetValue = if (selected) 64.dp else 12.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "Corner morph",
    )
    val width by animateDpAsState(
        targetValue = if (selected) 150.dp else 92.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "Width morph",
    )
    val color by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
        label = "Color morph",
    )
    ShowcaseSection("01", "Shape morphing", "State changes you can feel") {
        Box(Modifier.fillMaxWidth().height(170.dp), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .size(width = width, height = 92.dp)
                    .clip(RoundedCornerShape(corner))
                    .background(color)
                    .clickable(
                        role = Role.Button,
                        onClickLabel = if (selected) "Return to soft square" else "Morph into a pill",
                        onClick = { selected = !selected },
                    )
                    .semantics { stateDescription = if (selected) "Pill" else "Rounded square" },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.Favorite, null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(36.dp))
            }
        }
        Button(onClick = { selected = !selected }, modifier = Modifier.fillMaxWidth()) {
            Text(if (selected) "Return to soft square" else "Morph into a pill")
        }
        Caption("Corners, width and color interpolate together from one interaction.")
    }
}

@Composable
private fun SpringPlayground() {
    var moved by rememberSaveable { mutableStateOf(false) }
    var damping by rememberSaveable { mutableStateOf(Spring.DampingRatioMediumBouncy) }
    ShowcaseSection("02", "Spring playground", "Tune the bounce, then launch") {
        // The travel distance follows the measured track instead of a hard-coded 190.dp, which ran
        // past the card on small windows and made the object disappear.
        BoxWithConstraints(
            Modifier.fillMaxWidth().height(100.dp).clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
            contentAlignment = Alignment.CenterStart,
        ) {
            val travel = (maxWidth - 64.dp - 32.dp).coerceAtLeast(0.dp)
            val offset by animateDpAsState(
                targetValue = if (moved) travel else 0.dp,
                animationSpec = spring(dampingRatio = damping, stiffness = Spring.StiffnessMediumLow),
                label = "Spring playground",
            )
            Box(
                Modifier.padding(horizontal = 16.dp).offset(x = offset).size(64.dp)
                    .clip(RoundedCornerShape(22.dp)).background(MaterialTheme.colorScheme.primary),
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Bounce", Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
            Text(if (damping < .65f) "Playful" else "Subtle", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
        }
        Slider(value = damping, onValueChange = { damping = it }, valueRange = .45f..1f)
        FilledTonalButton(onClick = { moved = !moved }, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.Refresh, null)
            Spacer(Modifier.size(8.dp))
            Text("Launch spring")
        }
        Caption("Change the slider while the object is moving: the spring retargets smoothly.")
    }
}

@Composable
private fun MotionPrinciples() {
    ShowcaseSection("03", "Motion principles", "Expressive does not mean excessive") {
        Principle("Spatial", "Shape, position and size use expressive springs.")
        Principle("Effects", "Color and opacity remain fast and restrained.")
        Principle("Purposeful", "Every transition explains what changed and where it went.")
        Principle("Inclusive", "System animation-scale settings are honoured, so reduced motion snaps instead of bouncing.")
    }
}

@Composable
private fun Principle(title: String, body: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Text(body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
