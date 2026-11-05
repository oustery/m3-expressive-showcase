package com.example.m3expressive.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.m3expressive.ui.components.Caption
import com.example.m3expressive.ui.components.ScreenIntro
import com.example.m3expressive.ui.components.ShowcaseSection

@Composable
fun MotionScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            ScreenIntro("Physics, not keyframes", "Motion with\npersonality.", "Springs preserve velocity and respond naturally when a goal changes mid-animation.")
        }
        item { ShapeMorphExample() }
        item { SpringPlayground() }
        item { MotionPrinciples() }
    }
}

@Composable
private fun ShapeMorphExample() {
    var selected by remember { mutableStateOf(false) }
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
                Modifier.size(width = width, height = 92.dp).clip(RoundedCornerShape(corner)).background(color)
                    .clickable { selected = !selected },
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.Favorite, "Morph shape", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(36.dp))
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
    var moved by remember { mutableStateOf(false) }
    var damping by remember { mutableFloatStateOf(Spring.DampingRatioMediumBouncy) }
    val offset by animateDpAsState(
        targetValue = if (moved) 190.dp else 0.dp,
        animationSpec = spring(dampingRatio = damping, stiffness = Spring.StiffnessMediumLow),
        label = "Spring playground",
    )
    ShowcaseSection("02", "Spring playground", "Tune the bounce, then launch") {
        Box(
            Modifier.fillMaxWidth().height(100.dp).clip(MaterialTheme.shapes.medium).background(MaterialTheme.colorScheme.surfaceContainerHighest),
            contentAlignment = Alignment.CenterStart,
        ) {
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
            Icon(Icons.Default.Refresh, null); Spacer(Modifier.size(8.dp)); Text("Launch spring")
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
        Principle("Inclusive", "Respect reduced-motion settings in production experiences.")
    }
}

@Composable
private fun Principle(title: String, body: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Text(body, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
